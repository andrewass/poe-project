package com.poe.project.controllers

import com.poe.project.consumer.objects.TradeItemDTO
import com.poe.project.controllers.requests.FindTradeItemsRequest
import com.poe.project.entities.Item
import com.poe.project.entities.League
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

    @GetMapping("/leagues")
    @CrossOrigin("*")
    fun getActiveLeagues(): ResponseEntity<List<League>> {
        val leagues = poeService.findActiveLeagues()
        return ResponseEntity(leagues, HttpStatus.OK)
    }

    @GetMapping("/items")
    @CrossOrigin("*")
    fun getItems(): ResponseEntity<List<Item>> {
        val items = poeService.findItems()
        return ResponseEntity(items, HttpStatus.OK)
    }

    @PostMapping("/trade-items")
    @CrossOrigin("*")
    fun getTradeItems(@RequestBody findTradeItemsRequest: FindTradeItemsRequest)
            : ResponseEntity<List<TradeItemDTO>> {
        val tradeItems = poeService.findTradeItems(findTradeItemsRequest)
        return ResponseEntity(tradeItems, HttpStatus.OK)
    }

}