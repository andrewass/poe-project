package com.poe.project.repositories

import com.poe.project.entities.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    fun findItemByItemTextStartingWith(text : String) : List<Item>

}