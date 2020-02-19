package com.poe.project.repositories

import com.poe.project.entities.ItemType
import com.poe.project.entities.League
import com.poe.project.entities.tradeitem.PoeItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PoeItemRepository : JpaRepository<PoeItem, Long>{

    @Query("select distinct pi.itemName from PoeItem pi")
    fun getAllDistinctItemNames() : List<String>

    @Query("select distinct pi.itemName from PoeItem pi where pi.itemType in ?1")
    fun getDistinctByItemNameAndItemTypeIn(itemTypes : List<ItemType>) : List<String>

    fun findAllByItemNameAndLeague(itemName : String, league : League) : List<PoeItem>

    fun deleteAllByStashId(stashId : String)
}