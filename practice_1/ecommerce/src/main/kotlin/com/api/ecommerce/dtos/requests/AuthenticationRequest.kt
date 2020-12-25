package com.api.ecommerce.dtos.requests

import com.api.ecommerce.dtos.exceptions.StatusCode

class AuthenticationRequest(
    val userName: String,
    val password: String
) {
    fun validate(): StatusCode {
        if (userName.isEmpty()) return StatusCode.NameNotNull
        if (password.length < 6) return StatusCode.PasswordMinLength
//        if (password.length > 12) return StatusCode.PasswordMaxLength

        return StatusCode.OK
    }
}