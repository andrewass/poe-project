package com.poe.project.entities

import javax.persistence.*

@Entity
@Table(name = "T_INBOX")
class Inbox(

        @Id
        @GeneratedValue
        val id : Long? = null,

        @OneToOne
        val user : User? = null

)