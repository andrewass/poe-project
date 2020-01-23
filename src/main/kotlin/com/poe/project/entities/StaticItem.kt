package com.poe.project.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "T_STATIC_ITEM")
class StaticItem(
        @Id @GeneratedValue
        val id: Long? = null,

        val shortName: String = "",

        val fullName: String = "",

        val imageUrl: String = ""
)