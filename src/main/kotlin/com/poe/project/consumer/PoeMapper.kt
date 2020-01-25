package com.poe.project.consumer

import com.poe.project.entities.Item
import com.poe.project.entities.League
import com.poe.project.entities.StaticItem
import com.poe.project.entities.tradeitem.TradeItem
import org.json.JSONArray
import org.json.JSONObject


fun mapLeagues(leaguesJSON: JSONArray): List<League> {
    val leagues = mutableListOf<League>()
    for (i in 0 until leaguesJSON.length()) {
        val leagueJSON = leaguesJSON.getJSONObject(i)
        leagues.add(mapLeague(leagueJSON))
    }
    return leagues
}


fun mapItems(itemsJSON: JSONArray): List<Item> {
    val items = mutableListOf<Item>()
    for (i in 0 until itemsJSON.length()) {
        val item = itemsJSON.getJSONObject(i)
        items.addAll(mapLabel(item.getString("label"), item.getJSONArray("entries")))
    }
    return items
}

fun mapStaticItems(items: JSONArray, baseUrl: String): List<StaticItem> {
    val staticItemsDTO = mutableListOf<StaticItem>()
    val currencyArray = getCurrencyArray(items)
    for (i in 0 until currencyArray.length()) {
        val currency = currencyArray.getJSONObject(i)
        val staticItem = StaticItem(
                shortName = currency.getString("id"),
                fullName = currency.getString("text"),
                imageUrl = baseUrl + currency.getString("image")
        )
        staticItemsDTO.add(staticItem)
    }
    return staticItemsDTO
}


fun mapTradeItems(itemName: String, responseBody: String): List<TradeItem> {
    val items = JSONArray(responseBody)
    val tradeItems = mutableListOf<TradeItem>()
    for (i in 0 until items.length()) {
        val item = items.get(i)
        if (item is JSONObject) {
            val tradeItem = TradeItem(
                    name = itemName,

                    itemId = item.getString("id"),

                    currency = item.getJSONObject("listing")
                            .getJSONObject("price").getString("currency"),

                    currencyAmount = item.getJSONObject("listing")
                            .getJSONObject("price").getInt("amount"),

                    imageUrl = item.getJSONObject("item")
                            .getString("icon")
            )
            tradeItems.add(tradeItem)
        }
    }
    return tradeItems
}

private fun getCurrencyArray(staticItems: JSONArray): JSONArray {
    for (i in 0 until staticItems.length()) {
        val staticItem = staticItems.getJSONObject(i)
        if (staticItem.getString("id").equals("Currency")) {
            return staticItem.getJSONArray("entries")
        }
    }
    return JSONArray()
}

private fun mapLeague(leagueJSON: JSONObject): League {
    return League(
            name = leagueJSON.getString("text")
    )
}

private fun mapLabel(itemLabel: String, entries: JSONArray): List<Item> {
    val items = mutableListOf<Item>()
    for (i in 0 until entries.length()) {
        val entry = entries.getJSONObject(i)
        val item = Item(
                itemLabel = itemLabel,
                itemName = entry.optString("name"),
                itemType = entry.getString("type"),
                itemText = entry.getString("text"),
                uniqueItem = entry.optJSONObject("flags")?.optBoolean("unique") ?: false
        )
        items.add(item)
    }
    return items
}

