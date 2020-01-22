package com.poe.project.repositories

import com.poe.project.entities.StaticItem
import org.springframework.data.jpa.repository.JpaRepository

interface StaticItemRepository : JpaRepository<StaticItem, Long> {}