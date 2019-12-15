package com.transporter_server.service

import com.transporter_server.controllers.requests.SignInRequest
import com.transporter_server.controllers.requests.SignOutRequest
import com.transporter_server.controllers.requests.SignUpRequest
import com.transporter_server.controllers.responses.SignInResponse
import com.transporter_server.entities.User
import com.transporter_server.exceptions.IncorrectPasswordException
import com.transporter_server.exceptions.InvalidTokenException
import com.transporter_server.exceptions.UsernameNotAvailableException
import org.springframework.security.core.userdetails.UsernameNotFoundException

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