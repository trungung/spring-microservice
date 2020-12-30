package com.api.ecommerce.dtos.responses

class AuthenticationResponse(
    val username: String,
    val token: String,
    val role: String? = "")

//token, type, id, username, email, roles