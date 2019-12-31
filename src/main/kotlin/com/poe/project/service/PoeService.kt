package com.poe.project.service

import com.poe.project.consumer.objects.TradeItemDTO
import com.poe.project.controllers.requests.FindTradeItemsRequest
import com.poe.project.entities.Item
import com.poe.project.entities.League

interface PoEService {
    fun findActiveLeagues(): List<League>

    fun findItems(): List<Item>

    fun findTradeItems(findTradeItemsRequest: FindTradeItemsRequest): List<TradeItemDTO>
}