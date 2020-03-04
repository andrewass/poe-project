package com.poe.project.controllers.mapping

import com.poe.project.entities.ItemType
import com.poe.project.entities.StaticItem
import com.poe.project.entities.tradeitem.Modification
import com.poe.project.entities.tradeitem.Property

class PoeItemDto(
        val imageUrl: String,

        val itemName: String,

        val typeLine: String,

        val stashId: String,

        val identified: Boolean,

        val corrupted: Boolean,

        val itemType: ItemType?,

        val price: Pair<Int, StaticItem?>,

        val modifications : MutableList<Modification> =  mutableListOf(),

        val properties: MutableList<Property> = mutableListOf()
)