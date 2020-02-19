package com.poe.project.controllers

import com.poe.project.controllers.requests.StashFetchingRequest
import com.poe.project.controllers.requests.TradeItemsRequest
import com.poe.project.entities.League
import com.poe.project.entities.tradeitem.PoeItem
import com.poe.project.entities.StaticItem
import com.poe.project.service.PoEService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/poe")
class PoEController @Autowired constructor(
        private val poeService: PoEService
) {

    @PostMapping("/start-stash-fetch")
    @CrossOrigin("*")
    fun startStashFetching(@RequestBody request: StashFetchingRequest): HttpStatus {
        poeService.findActiveLeagues()
        poeService.findStaticItems()
        poeService.fetchStashItems(request.fetchId)
        return HttpStatus.OK
    }

    @PostMapping("/stop-stash-fetch")
    fun stopStashFetching(): HttpStatus {
        poeService.stopStashFetching()
        return HttpStatus.OK
    }

    @GetMapping("/leagues")
    @CrossOrigin("*")
    fun getActiveLeagues(): ResponseEntity<List<League>> {
        val leagues = poeService.findActiveLeagues()
        return ResponseEntity(leagues, HttpStatus.OK)
    }

    @GetMapping("/static-items")
    @CrossOrigin("*")
    fun getStaticItems(): ResponseEntity<List<StaticItem>> {
        val staticItems = poeService.findStaticItems()
        return ResponseEntity(staticItems, HttpStatus.OK)
    }

    @GetMapping("/available-items")
    @CrossOrigin("*")
    fun getAvailableItems(): ResponseEntity<List<String>> {
        val availableItems = poeService.findTradeItemNames()
        return ResponseEntity(availableItems, HttpStatus.OK)
    }

    @PostMapping("/trade-items")
    @CrossOrigin("*")
    fun getTradeItems(@RequestBody request: TradeItemsRequest) : ResponseEntity<List<PoeItem>> {
        val tradeItems = poeService.findTradeItems(request.name, request.league)
        return ResponseEntity(tradeItems, HttpStatus.OK)
    }

}