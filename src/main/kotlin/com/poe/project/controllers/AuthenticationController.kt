package com.poe.project.controllers

import com.poe.project.controllers.requests.SignInRequest
import com.poe.project.controllers.requests.SignUpRequest
import com.poe.project.controllers.responses.SignInResponse
import com.poe.project.exceptions.UsernameNotAvailableException
import com.poe.project.service.AuthenticationService
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