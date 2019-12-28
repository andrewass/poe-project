package com.poe.project.entities

import javax.persistence.*

@Entity
@Table(name = "T_USER")
class User(
        @Id @GeneratedValue
        val id : Long? = null,

        val username : String = "",

        val password : String = "",

        val email : String = ""
)