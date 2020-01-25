package com.poe.project.service

import com.poe.project.consumer.PoEConsumer
import com.poe.project.controllers.requests.FindTradeItemsRequest
import com.poe.project.entities.Item
import com.poe.project.entities.League
import com.poe.project.entities.StaticItem
import com.poe.project.entities.tradeitem.TradeItem
import com.poe.project.repositories.ItemRepository
import com.poe.project.repositories.LeagueRepository
import com.poe.project.repositories.StaticItemRepository
import com.poe.project.repositories.TradeItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomPoEService @Autowired constructor(
        private val leagueRepository: LeagueRepository,
        private val itemRepository: ItemRepository,
        private val tradeItemRepository: TradeItemRepository,
        private val poeConsumer: PoEConsumer,
        private val staticItemRepository: StaticItemRepository
) : PoEService {

    override fun findActiveLeagues(): List<League> {
        val activeLeagues = poeConsumer.getLeagues()
        val storedActiveLeagues = leagueRepository.findByActiveIsTrue()
        setInactiveLeagues(activeLeagues, storedActiveLeagues)
        addNewLeagues(activeLeagues, storedActiveLeagues)

        return leagueRepository.findByActiveIsTrue()
    }

    override fun findItems(): List<Item> {
        val fetchedItems = poeConsumer.getItems()
        itemRepository.deleteAll()

        return itemRepository.saveAll(fetchedItems)
    }

    override fun findTradeItems(request: FindTradeItemsRequest): List<TradeItem> {
        val tradeItems = poeConsumer.findItemsForTrade(itemName = request.name, league = request.league)
        return tradeItemRepository.saveAll(tradeItems)

    }

    override fun findStaticItems(): List<StaticItem> {
        val staticItems = poeConsumer.getStaticItems()
        return staticItemRepository.saveAll(staticItems)
    }

    private fun setInactiveLeagues(activeLeagues: List<League>, storedActiveLeagues: List<League>) {
        storedActiveLeagues
                .filter { league -> activeLeagues.none { league.name == it.name } }
                .forEach {
                    it.active = false
                    leagueRepository.save(it)
                }
    }

    private fun addNewLeagues(activeLeagues: List<League>, storedActiveLeagues: List<League>) {
        activeLeagues
                .filter { league -> storedActiveLeagues.none { league.name == it.name } }
                .forEach { leagueRepository.save(it) }
    }

}