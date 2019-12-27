package com.poe.project.service

import com.poe.project.entities.Currency
import com.poe.project.entities.League

interface PoEService
{

    fun findActiveLeagues(): List<League>

    fun findAllCurrencies() : List<Currency>

}