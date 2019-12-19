package com.transporter_server.controllers.responses

import com.transporter_server.entities.User

class SignInResponse(val user: User, val token: String)