package com.poe.project.consumer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PoEConsumerTest {

    @Autowired
    lateinit var poeConsumer : PoEConsumer

    @Test
    fun shouldReturnStatus200WhenFetchingSeasons() {
        poeConsumer.getLeagues()
    }
}