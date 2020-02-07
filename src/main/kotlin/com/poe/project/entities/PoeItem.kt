package com.poe.project.entities

import javax.persistence.*

@Entity
@Table(name = "T_ITEM")
class PoeItem(
        @Id @GeneratedValue
        val id: Long? = null

        /*
        @ManyToOne
        @JoinColumn(name = "STASH_ID")
        val stash : Stash? = null
        */
)
