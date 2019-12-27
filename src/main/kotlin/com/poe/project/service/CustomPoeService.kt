package com.poe.project.service

import com.poe.project.consumer.PoEConsumer
import com.poe.project.consumer.objects.LeagueDTO
import com.poe.project.entities.Currency
import com.poe.project.entities.League
import com.poe.project.repositories.LeagueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CustomPoEService @Autowired constructor(
        private val leagueRepository: LeagueRepository,
        private val poeConsumer: PoEConsumer
) : PoEService {

    override fun findActiveLeagues(): List<League> {
        val activeLeagues = poeConsumer.getLeagues()
        val storedActiveLeagues = leagueRepository.findByActiveIsTrue()
        setInactiveLeagues(activeLeagues, storedActiveLeagues)
        addNewLeagues(activeLeagues, storedActiveLeagues)
        return leagueRepository.findByActiveIsTrue()
    }

    override fun findAllCurrencies(): List<Currency> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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