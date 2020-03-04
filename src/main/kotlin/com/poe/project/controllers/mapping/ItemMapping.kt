package com.poe.project.controllers.mapping

import com.poe.project.entities.tradeitem.PoeItem



fun mapToPoeItemDto(items: List<PoeItem>): List<PoeItemDto> {
    val poeItemDtoList = mutableListOf<PoeItemDto>()
    items.forEach { item ->
        val poeItemDto = item.mapToDto()
        item.properties.forEach {
            poeItemDto.properties.add(it)
        }
        item.modifications.forEach {
            poeItemDto.modifications.add(it)
        }
        poeItemDtoList.add(poeItemDto)
    }
    return poeItemDtoList
}


fun PoeItem.mapToDto(): PoeItemDto {
    return PoeItemDto(
            imageUrl = imageUrl,
            itemName = itemName,
            typeLine = typeLine,
            stashId = stashId,
            identified = identified,
            corrupted = corrupted,
            itemType = itemType,
            price = getPrice()
    )
}
