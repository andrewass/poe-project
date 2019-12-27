package com.poe.project.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class CustomPasswordEncoderTest{

    private val rawPassword = "p@ssWord1234"

    private val passwordEncoder = CustomPasswordEncoder()

    @Test
    fun shouldEncodePasswordWithExpectedResult(){
        val firstEncoded = passwordEncoder.encode(rawPassword)
        val secondEncoded = passwordEncoder.encode(rawPassword)
        assertEquals(firstEncoded, secondEncoded)
    }

    @Test
    fun shouldMatchSamePassword(){
        val encoded = passwordEncoder.encode(rawPassword)
        assertEquals(true, passwordEncoder.matches(rawPassword, encoded))
    }

    @Test
    fun shouldNotMatchWhenPasswordsAreNotTheSame(){
        val encoded = passwordEncoder.encode("password1234")
        assertEquals(false, passwordEncoder.matches(rawPassword, encoded))
    }
}