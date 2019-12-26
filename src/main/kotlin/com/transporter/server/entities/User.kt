package com.transporter.server.entities

import javax.persistence.*

@Entity
@Table(name = "T_USER")
class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Long? = null,

        val username : String = "",

        val password : String = "",

        val email : String = ""
)