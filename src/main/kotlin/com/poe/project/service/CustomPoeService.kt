package com.poe.project.service

import com.poe.project.consumer.PoEConsumer
import com.poe.project.consumer.objects.ItemDTO
import com.poe.project.consumer.objects.LeagueDTO
import com.poe.project.entities.Item
import com.poe.project.entities.League
import com.poe.project.repositories.ItemRepository
import com.poe.project.repositories.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomPoEService @Autowired constructor(
        private val leagueRepository: LeagueRepository,
        private val itemRepository: ItemRepository,
        private val poeConsumer: PoEConsumer
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
        return itemRepository.saveAll(convertToItems(fetchedItems))
    }

    private fun convertToItems(fetchedItems: List<ItemDTO>): List<Item> {
        return fetchedItems
                .map { convertToItem(it) }
                .toList()
    }

    private fun convertToItem(item: ItemDTO): Item {
        return Item(
                itemName = item.name ?: "",
                itemText = item.text,
                uniqueItem = item.unique ?: false,
                itemType = item.type,
                itemLabel = item.label ?: ""
        )
    }

    private fun setInactiveLeagues(activeLeagues: List<LeagueDTO>, storedActiveLeagues: List<League>) {
        storedActiveLeagues
                .filter { league -> activeLeagues.none { league.name == it.name } }
                .forEach {
                    it.active = false
                    leagueRepository.save(it)
                }
    }

    private fun addNewLeagues(activeLeagues: List<LeagueDTO>, storedActiveLeagues: List<League>) {
        activeLeagues
                .filter { league -> storedActiveLeagues.none { league.name == it.name } }
                .map { convertToLeague(it) }
                .forEach { leagueRepository.save(it) }
    }

    private fun convertToLeague(leagueDTO: LeagueDTO) = League(name = leagueDTO.name)
}