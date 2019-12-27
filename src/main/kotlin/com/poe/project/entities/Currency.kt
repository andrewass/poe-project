package com.poe.project.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Currency(
        @Id @GeneratedValue
        val id: Long? = null,

        val name: String = ""

)