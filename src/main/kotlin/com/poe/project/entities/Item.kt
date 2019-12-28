package com.poe.project.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "T_ITEM")
class Item(
        @Id @GeneratedValue
        val id: Long? = null,

        val itemLabel: String = "",

        val itemName: String = "",

        val itemType: String = "",

        val itemText: String = "",

        val uniqueItem: Boolean = false
)