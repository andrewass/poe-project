package com.poe.project.entities.tradeitem

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_SOCKET")
class Socket(
        @Id @GeneratedValue
        val id: Long? = null,

        val group: Int = 0,

        val color : String
)