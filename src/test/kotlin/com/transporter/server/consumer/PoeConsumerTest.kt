package com.transporter.server.consumer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PoeConsumerTest {

    @Autowired
    lateinit var poeConsumer : PoeConsumer

    @Test
    fun shouldReturnStatus200WhenFetchingSeasons() {
        poeConsumer.getLeagues()
    }
}