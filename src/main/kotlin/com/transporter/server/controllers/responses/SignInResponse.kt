package com.transporter.server.controllers.responses

import com.transporter.server.entities.User

class SignInResponse(val user: User, val token: String)