package com.transporter_server.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.transporter_server.controllers.requests.SignUpRequest
import com.transporter_server.controllers.responses.SignInResponse
import com.transporter_server.entities.User
import com.transporter_server.service.AuthenticationService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(controllers = [AuthenticationController::class])
class AuthenticationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var authService: AuthenticationService

    @TestConfiguration
    class TestConfig {
        @Bean
        fun authService() = mockk<AuthenticationService>()
    }

    @Test
    @Throws(Exception::class)
    fun shouldReturnStatusOkWhenUsernameIsAvailableForSignUp() {

        val signedUpUser = User()
        val signedInResponse = SignInResponse(signedUpUser, "testtoken")

        every { authService.createUser(any()) } returns signedUpUser
        every { authService.signInUser(any()) } returns signedInResponse

        mockMvc.perform(
                post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSignUpRequest()))
                .andExpect(status().isOk)
    }

    private fun createSignUpRequest(): String {
        val request = SignUpRequest("testuser", "p@sswOrd", "test@mail.com")
        return objectMapper.writeValueAsString(request)
    }

}