package com.poe.project.service

import com.poe.project.controllers.requests.FindTradeItemsRequest
import com.poe.project.entities.Item
import com.poe.project.entities.League
import com.poe.project.entities.StaticItem
import com.poe.project.entities.tradeitem.TradeItem

interface PoEService {
    fun findActiveLeagues(): List<League>

    fun findItems(): List<Item>

    fun findTradeItems(request: FindTradeItemsRequest): List<TradeItem>

    fun findStaticItems(): List<StaticItem>
}