package com.poe.project.service

import com.poe.project.entities.Item
import com.poe.project.entities.League

interface PoEService
{
    fun findActiveLeagues(): List<League>

    fun findItems(): List<Item>
}