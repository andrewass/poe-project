package com.transporter.server.service

import com.transporter.server.entities.Currency
import com.transporter.server.entities.League

interface PoeService {

    fun findActiveLeagues(): List<League>

    fun findAllCurrencies() : List<Currency>

}