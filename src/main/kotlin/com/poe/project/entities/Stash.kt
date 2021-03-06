package com.poe.project.entities

import com.poe.project.entities.tradeitem.PoeItem

class Stash(
        val id: String,
        val stashText: String,
        val stashType: String,
        val league: League?,
        val accountName: String,
        val lastCharacterName: String

) {
    val items = mutableListOf<PoeItem>()
}

