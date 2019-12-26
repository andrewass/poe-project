package com.transporter.server.service

import com.transporter.server.controllers.requests.SignInRequest
import com.transporter.server.controllers.requests.SignOutRequest
import com.transporter.server.controllers.requests.SignUpRequest
import com.transporter.server.controllers.responses.SignInResponse
import com.transporter.server.entities.User
import com.transporter.server.exceptions.IncorrectPasswordException
import com.transporter.server.exceptions.InvalidTokenException
import com.transporter.server.exceptions.UsernameNotAvailableException
import com.transporter.server.exceptions.UsernameNotFoundException


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