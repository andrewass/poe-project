package com.poe.project.service

import com.poe.project.controllers.requests.SignInRequest
import com.poe.project.controllers.requests.SignOutRequest
import com.poe.project.controllers.requests.SignUpRequest
import com.poe.project.controllers.responses.SignInResponse
import com.poe.project.entities.User
import com.poe.project.exceptions.IncorrectPasswordException
import com.poe.project.exceptions.InvalidTokenException
import com.poe.project.exceptions.UsernameNotAvailableException
import com.poe.project.exceptions.UsernameNotFoundException


interface AuthenticationService {

    @Throws(UsernameNotAvailableException::class)
    fun createUser(request : SignUpRequest) : User

    @Throws(IncorrectPasswordException::class, UsernameNotFoundException::class)
    fun signInUser(request: SignInRequest) : SignInResponse

    @Throws(InvalidTokenException::class)
    fun signOutUser(request : SignOutRequest)

    @Throws(InvalidTokenException::class)
    fun getUserFromToken(token : String) : User
}