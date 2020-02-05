package com.poe.project.controllers

import com.poe.project.controllers.requests.PublicMessageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kafka")
class MessageController @Autowired constructor(
        private val kafkaTemplate: KafkaTemplate<String, String>
) {


    @PostMapping("/publish")
    fun postMessage(@RequestBody request: PublicMessageRequest): ResponseEntity<HttpStatus> {

        kafkaTemplate.send(request.topic, request.message)

        return ResponseEntity(HttpStatus.OK)
    }
}