package com.poe.project.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_LEAGUE")
class League(
        @Id @GeneratedValue
        val id: Long? = null,

        val name: String = "",

        var active: Boolean = true
)