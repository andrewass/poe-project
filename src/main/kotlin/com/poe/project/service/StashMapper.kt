package com.poe.project.service

import com.poe.project.entities.ItemType
import com.poe.project.entities.Stash
import com.poe.project.entities.StaticItem
import com.poe.project.entities.tradeitem.Modification
import com.poe.project.entities.tradeitem.PoeItem
import com.poe.project.entities.tradeitem.Property
import com.poe.project.repositories.LeagueRepository
import com.poe.project.repositories.StaticItemRepository
import com.poe.project.service.response.StashResponse
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StashMapper @Autowired constructor(
        private val leagueRepository: LeagueRepository,
        private val staticItemRepository: StaticItemRepository
) {

    private val emptyResponse = ""
    lateinit var currencies: List<StaticItem>

    fun createStashResponse(apiResponse: String): StashResponse {
        if (apiResponse == emptyResponse) {
            return StashResponse()
        }
        currencies = staticItemRepository.findAll()
        val jsonResponse = JSONObject(apiResponse)
        val stashResponse = StashResponse(nextChangeId = jsonResponse.getString("next_change_id"))
        val stashes = jsonResponse.getJSONArray("stashes")

        for (i in 0 until stashes.length()) {
            (mapStash(stashes.getJSONObject(i), stashResponse))
        }
        return stashResponse
    }

    private fun mapStash(jsonStash: JSONObject, stashResponse: StashResponse) {
        if (jsonStash.getBoolean("public")) {
            val stash = Stash(
                    id = jsonStash.getString("id"),
                    accountName = jsonStash.getString("accountName"),
                    lastCharacterName = jsonStash.getString("lastCharacterName"),
                    stashText = jsonStash.getString("stash"),
                    stashType = jsonStash.getString("stashType"),
                    league = leagueRepository.findLeagueByName(jsonStash.getString("league").toUpperCase())
            )
            val stashPrice = extractPrice(stash.stashText)
            val items = jsonStash.getJSONArray("items")
            for (i in 0 until items.length()) {
                mapItems(stash, items.getJSONObject(i), stashPrice)
            }
            stashResponse.stashes.add(stash)
        }
    }

    private fun mapItems(stash: Stash, jsonItem: JSONObject, stashPrice: Pair<Int, StaticItem?>) {
        val itemName = jsonItem.getString("name")
        if (itemName.isNotEmpty()) {
            val poeItem = PoeItem(
                    imageUrl = jsonItem.getString("icon"),
                    textID = jsonItem.getString("id"),
                    itemName = itemName,
                    typeLine = jsonItem.getString("typeLine"),
                    identified = jsonItem.getBoolean("identified"),
                    corrupted = jsonItem.optBoolean("corrupted"),
                    stashId = stash.id,
                    league = stash.league,
                    itemType = mapItemType(jsonItem.getInt("frameType"))
            )
            val priceNote = jsonItem.optString("note", emptyResponse)
            val itemPrice = extractPrice(priceNote)
            poeItem.setPrice(itemPrice = itemPrice, stashPrice = stashPrice)
            if (poeItem.getPrice().first > 0) {
                stash.items.add(poeItem)
            }
            mapProperties(poeItem, jsonItem.optJSONArray("properties") ?: JSONArray())
            mapModifications(poeItem, jsonItem.optJSONArray("implicitMods") ?: JSONArray(), isImplicit = true)
            mapModifications(poeItem, jsonItem.optJSONArray("explicitMods") ?: JSONArray(), isImplicit = false)
        }
    }

    private fun mapProperties(poeItem: PoeItem, properties: JSONArray) {
        for (i in 0 until properties.length()) {
            val propertyJson = properties.getJSONObject(i)
            val property = Property(
                    item = poeItem,
                    description = propertyJson.getString("name")
            )
            poeItem.properties.add(property)
        }
    }

    private fun mapModifications(poeItem: PoeItem, modifications: JSONArray, isImplicit: Boolean) {
        for (i in 0 until modifications.length()) {
            val description = modifications.getString(i)
            val modification = Modification(
                    item = poeItem,
                    description = description,
                    isImplicit = isImplicit
            )
            poeItem.modifications.add(modification)
        }
    }

    private fun mapItemType(typeNumber: Int): ItemType? {
        return enumValues<ItemType>().find { it.number == typeNumber }
    }

    private fun extractPrice(description: String): Pair<Int, StaticItem?> {
        val parts = description.split(" ")
        for (i in 1 until parts.size) {
            val numberString = parts[i - 1].toIntOrNull()
            val currency = getCurrency(parts[i])
            if (numberString != null && currency != null) {
                return Pair(numberString, currency)
            }
        }
        return Pair(0, null)
    }


    private fun getCurrency(description: String): StaticItem? {
        return currencies.firstOrNull { it.shortName.equals(description, true) }

    }
}