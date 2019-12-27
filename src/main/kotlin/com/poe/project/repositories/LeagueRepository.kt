package com.poe.project.repositories

import com.poe.project.entities.League
import org.springframework.data.jpa.repository.JpaRepository

interface LeagueRepository : JpaRepository<League, Long> {

    fun findByName(name : String) : League?

    fun findByActiveIsTrue() : List<League>

}