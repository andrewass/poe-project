package com.transporter.server.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class League(
        @Id @GeneratedValue
        val id: Long? = null,

        val name: String = ""
)