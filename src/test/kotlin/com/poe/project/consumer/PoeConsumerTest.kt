package com.poe.project.consumer

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PoEConsumerTest {

    @Autowired
    lateinit var poeConsumer: PoEConsumer

    @Test
    fun shouldReturnStatus200WhenFetchingSeasons() {
        poeConsumer.getLeagues()
    }

    @Test
    fun shouldReturnStatus200WhenFetchingItems() {
        val items = poeConsumer.getItems()
    }

    @Test
    fun shouldReturnStatus200WhenFetchingStaticItems() {
        val staticItems = poeConsumer.getStaticItems()
    }

    @Test
    fun shouldReturnStatus200WhenFetchingNamedItem() {
        val result = poeConsumer.findItemsForTrade("Tabula Rasa", "Standard")
        val test = 11
    }
}