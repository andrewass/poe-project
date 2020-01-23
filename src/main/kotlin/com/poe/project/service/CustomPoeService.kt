package com.poe.project.service

import com.poe.project.consumer.PoEConsumer
import com.poe.project.consumer.objects.ItemDTO
import com.poe.project.consumer.objects.LeagueDTO
import com.poe.project.consumer.objects.StaticItemDTO
import com.poe.project.consumer.objects.TradeItemDTO
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

        return itemRepository.saveAll(convertToItems(fetchedItems))
    }

    override fun findTradeItems(request: FindTradeItemsRequest): List<TradeItemDTO> {
        val resultDTO = poeConsumer.findItemsForTrade(itemName = request.name, league = request.league)
        storeTradeItems(resultDTO)
        return resultDTO
    }

    override fun findStaticItems(): List<StaticItem> {
        val staticItemsDto = poeConsumer.getStaticItems()
        return staticItemRepository.saveAll(convertToStaticItems(staticItemsDto))
    }


    private fun storeTradeItems(fetchedItems: List<TradeItemDTO>) {
        val itemsToSave = fetchedItems
                .filter { tradeItemRepository.findByItemId(it.id) == null }
                .map { convertToTradeItem(it) }
                .toList()

        tradeItemRepository.saveAll(itemsToSave)
    }

    private fun convertToStaticItems(staticItemsDto: List<StaticItemDTO>): List<StaticItem> {
        return staticItemsDto
                .map { convertToStaticItem(it) }
                .toList()
    }

    private fun convertToItems(fetchedItems: List<ItemDTO>): List<Item> {
        return fetchedItems
                .map { convertToItem(it) }
                .toList()
    }

    private fun convertToStaticItem(staticItemDto : StaticItemDTO) : StaticItem {
        return StaticItem(
                shortName = staticItemDto.id,
                fullName = staticItemDto.text,
                imageUrl = staticItemDto.image
        )
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

    private fun convertToTradeItem(item: TradeItemDTO): TradeItem {
        return TradeItem(
                itemId = item.id,
                name = item.name,
                currency = item.currency,
                currencyAmount = item.currencyAmount
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