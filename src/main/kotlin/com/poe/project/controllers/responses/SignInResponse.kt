package com.poe.project.controllers.responses

import com.poe.project.entities.User

class SignInResponse(val user: User, val token: String)