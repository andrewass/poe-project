package com.poe.project.service

import com.poe.project.entities.Stash
import com.poe.project.entities.StaticItem
import com.poe.project.repositories.LeagueRepository
import com.poe.project.repositories.StaticItemRepository
import com.poe.project.service.response.StashResponse
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
                    league = leagueRepository.findLeagueByName(jsonStash.getString("league").toUpperCase())!!
            )
            val stashPrice = extractPrice(stash.stashText)
            val items = jsonStash.getJSONArray("items")

            stashResponse.stashes.add(stash)
        }
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