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
import com.poe.project.repositories.UserRepository
import com.poe.project.util.PasswordEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomAuthService @Autowired constructor(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) : AuthenticationService {

    val userTokenMap = hashMapOf<String, User>()

    override fun createUser(request: SignUpRequest): User {
        if (usernameIsUnavailable(request.username)) {
            throw UsernameNotAvailableException("Username : ${request.username}")
        }
        val user = User(
                username = request.username,
                password = passwordEncoder.encode(request.password),
                email = request.email
        )
        return userRepository.save(user)
    }

    override fun signInUser(request: SignInRequest): SignInResponse {
        val user = findUser(request.username)
        authenticateUser(user!!, request.password)
        val token = generateUserToken()
        return SignInResponse(user, token)
    }

    override fun signOutUser(request: SignOutRequest) {
        userTokenMap.remove(request.token)
    }

    override fun getUserFromToken(token: String): User {
        return userTokenMap[token] ?: throw InvalidTokenException("Token : $token")
    }

    private fun generateUserToken(): String {
        var token: String
        do {
            token = UUID.randomUUID().toString()
        } while (userTokenMap.containsKey(token))

        return token
    }

    private fun authenticateUser(user: User, submittedPassword: String) {
        if (!passwordEncoder.matches(submittedPassword, user.password)) {
            throw IncorrectPasswordException("User : ${user.username}")
        }
    }

    private fun usernameIsUnavailable(username: String): Boolean {
        return userRepository.findByUsername(username) != null
    }

    private fun findUser(username: String): User? {
        return userRepository.findByUsername(username) ?: throw UsernameNotFoundException("Username : $username")
    }
}
