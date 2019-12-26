package com.transporter.server.util

interface PasswordEncoder {

    fun encode(password : String) : String

    fun matches(submitted : String, stored : String) : Boolean
}