package com.poe.project.service

import com.poe.project.entities.League
import com.poe.project.entities.tradeitem.PoeItem
import com.poe.project.entities.StaticItem

interface PoEService {
    fun findActiveLeagues(): List<League>

    fun findStaticItems(): List<StaticItem>

    fun fetchStashItems(fetchId: String)

    fun findTradeItemNames() : List<String>

    fun stopStashFetching()

    fun findTradeItems(itemName: String, leagueName: String): List<PoeItem>
}