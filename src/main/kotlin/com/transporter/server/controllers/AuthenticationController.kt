package com.transporter.server.controllers

import com.transporter.server.controllers.requests.SignInRequest
import com.transporter.server.controllers.requests.SignUpRequest
import com.transporter.server.controllers.responses.SignInResponse
import com.transporter.server.exceptions.UsernameNotAvailableException
import com.transporter.server.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController @Autowired constructor(
        private val authenticationService: AuthenticationService
) {

    @PostMapping("/sign-up")
    @CrossOrigin("*")
    fun signUpUser(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<SignInResponse> {
        return try {
            authenticationService.createUser(signUpRequest)
            val signInRequest = SignInRequest(signUpRequest.username, signUpRequest.password)
            val signInResponse = authenticationService.signInUser(signInRequest)
            ResponseEntity(signInResponse, HttpStatus.OK)
        } catch (e: UsernameNotAvailableException) {
            ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/sign-in")
    @CrossOrigin("**")
    fun signInUser(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        return ResponseEntity(HttpStatus.OK)
    }
}