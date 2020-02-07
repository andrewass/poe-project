package com.poe.project.entities

class Stash(
        val id: String,
        val stashText: String,
        val stashType: String,
        val league: League,
        accountName: String,
        lastCharacterName: String

) {
    val items = mutableListOf<PoeItem>()
}

