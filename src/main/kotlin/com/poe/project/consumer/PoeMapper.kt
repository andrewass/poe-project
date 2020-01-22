package com.poe.project.consumer

import com.poe.project.consumer.objects.ItemDTO
import com.poe.project.consumer.objects.StaticItemDTO
import com.poe.project.consumer.objects.TradeItemDTO
import org.json.JSONArray
import org.json.JSONObject


fun mapItems(items: JSONArray): List<ItemDTO> {
    val itemsDTO = mutableListOf<ItemDTO>()
    for (i in 0 until items.length()) {
        val item = items.getJSONObject(i)
        itemsDTO.addAll(mapLabel(item.getString("label"), item.getJSONArray("entries")))
    }
    return itemsDTO
}

fun mapStaticItems(items: JSONArray, baseUrl : String): List<StaticItemDTO> {
    val staticItemsDTO = mutableListOf<StaticItemDTO>()
    val currencyArray = getCurrencyArray(items)
    for (i in 0 until currencyArray.length()) {
        val currency = currencyArray.getJSONObject(i)
        val staticItem = StaticItemDTO(
                id = currency.getString("id"),
                text = currency.getString("text"),
                image = baseUrl + currency.getString("image")
        )
        staticItemsDTO.add(staticItem)
    }
    return staticItemsDTO
}


fun mapTradeItems(itemName: String, responseBody: String): List<TradeItemDTO> {
    val items = JSONArray(responseBody)
    val tradeItems = mutableListOf<TradeItemDTO>()
    for (i in 0 until items.length()) {
        val item = items.get(i)
        if (item is JSONObject) {
            val tradeItem = TradeItemDTO(
                    name = itemName,

                    id = item.getString("id"),

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

private fun mapLabel(itemLabel: String, entries: JSONArray): List<ItemDTO> {
    val itemsDTO = mutableListOf<ItemDTO>()
    for (i in 0 until entries.length()) {
        val entry = entries.getJSONObject(i)
        val item = ItemDTO(
                label = itemLabel,
                name = entry.optString("name"),
                type = entry.getString("type"),
                text = entry.getString("text"),
                unique = entry.optJSONObject("flags")?.optBoolean("unique")
        )
        itemsDTO.add(item)
    }
    return itemsDTO
}

