package com.poe.project.consumer

import com.poe.project.entities.League
import com.poe.project.entities.StaticItem
import org.json.JSONArray


fun mapLeagues(leaguesJSON: JSONArray): List<League> {
    val leagues = mutableListOf<League>()
    for (i in 0 until leaguesJSON.length()) {
        val leagueJSON = leaguesJSON.getJSONObject(i)
        leagues.add(League(name = leagueJSON.getString("text").toUpperCase()))
    }
    return leagues
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

private fun getCurrencyArray(staticItems: JSONArray): JSONArray {
    for (i in 0 until staticItems.length()) {
        val staticItem = staticItems.getJSONObject(i)
        if (staticItem.getString("id") == "Currency") {
            return staticItem.getJSONArray("entries")
        }
    }
    return JSONArray()
}


