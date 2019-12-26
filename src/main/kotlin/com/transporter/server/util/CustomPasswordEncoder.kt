package com.transporter.server.util

import java.security.MessageDigest


class CustomPasswordEncoder : PasswordEncoder {

    override fun encode(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(password.toByteArray())
        return String(messageDigest.digest())
    }

    override fun matches(submitted: String, stored: String): Boolean {
        val encoded = encode(submitted)
        return encoded == stored
    }
}