package com.poe.project.service

import com.poe.project.consumer.PoEConsumer
import com.poe.project.entities.League
import com.poe.project.entities.Stash
import com.poe.project.entities.StaticItem
import com.poe.project.repositories.LeagueRepository
import com.poe.project.repositories.PoeItemRepository
import com.poe.project.repositories.StaticItemRepository
import com.poe.project.service.response.StashResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomPoEService @Autowired constructor(
        private val leagueRepository: LeagueRepository,
        private val poeConsumer: PoEConsumer,
        private val staticItemRepository: StaticItemRepository,
        private val poeItemRepository: PoeItemRepository,
        private val stashMapper: StashMapper
) : PoEService {

    private var keepFetchingStashTabs = false
    private val log = LoggerFactory.getLogger(CustomPoEService::class.java)

    override fun findActiveLeagues(): List<League> {
        val activeLeagues = poeConsumer.getLeagues()
        val storedActiveLeagues = leagueRepository.findByActiveIsTrue()
        setInactiveLeagues(activeLeagues, storedActiveLeagues)
        addNewLeagues(activeLeagues, storedActiveLeagues)

        return leagueRepository.findByActiveIsTrue()
    }

    override fun findStaticItems(): List<StaticItem> {
        val staticItems = poeConsumer.getStaticItems()
        return staticItemRepository.saveAll(staticItems)
    }

    override fun fetchStashItems(fetchId: String) {
        if (keepFetchingStashTabs) {
            log.info("Fetching of stash items already in progress. Stop current fetch process before starting a new one")
            return
        }
        var nextChangeId = fetchId
        keepFetchingStashTabs = true
        Thread {
            while (keepFetchingStashTabs) {
                log.info("Fetching next page of stash tabs, using id $nextChangeId")
                val stashResponse = mapResponse(poeConsumer.parseStashTabs(nextChangeId))
                poeItemRepository.saveAll(stashResponse.stashes.flatMap { it.items })
                nextChangeId = stashResponse.nextChangeId
                Thread.sleep(10000)
            }
        }.start()
    }

    override fun stopStashFetching() {
        log.info("Stopping stash tab fetching")
        keepFetchingStashTabs = false
    }

    private fun mapResponse(apiResponse: String): StashResponse {
        return stashMapper.createStashResponse(apiResponse)
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