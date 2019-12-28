package com.poe.project.consumer

import com.poe.project.consumer.objects.ItemDTO
import org.json.JSONArray


fun mapItems(items: JSONArray): List<ItemDTO> {
    val itemsDTO = mutableListOf<ItemDTO>()
    for (i in 0 until items.length()) {
        val item = items.getJSONObject(i)
        itemsDTO.addAll(mapLabel(item.getString("label"), item.getJSONArray("entries")))
    }
    return itemsDTO
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
