package com.poe.project.repositories

import com.poe.project.entities.PoeItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<PoeItem, Long> {



}