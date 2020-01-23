package com.poe.project.repositories

import com.poe.project.entities.tradeitem.TradeItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeItemRepository : JpaRepository<TradeItem, Long> {

    fun findByItemId(itemId : String) : TradeItem?

}