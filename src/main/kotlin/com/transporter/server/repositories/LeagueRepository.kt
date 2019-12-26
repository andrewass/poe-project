package com.transporter.server.repositories

import com.transporter.server.entities.League
import org.springframework.data.jpa.repository.JpaRepository

interface LeagueRepository : JpaRepository<League, Long> {

    fun findByName(name : String) : League?

}