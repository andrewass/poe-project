package com.poe.project.controllers

import com.poe.project.entities.League
import com.poe.project.service.PoEService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/poe")
class PoEController @Autowired constructor(
        private val poeService: PoEService
) {

    @GetMapping("/leagues")
    @CrossOrigin("*")
    fun getActiveLeagues(): ResponseEntity<List<League>> {
        val leagueList = poeService.findActiveLeagues()
        return ResponseEntity(leagueList, HttpStatus.OK)
    }
}