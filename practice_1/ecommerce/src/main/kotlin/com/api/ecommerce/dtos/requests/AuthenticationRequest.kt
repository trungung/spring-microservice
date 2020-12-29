package com.api.ecommerce.dtos.requests

import com.api.ecommerce.errors.StatusCode

class AuthenticationRequest(
    val userName: String,
    val password: String
) {
    fun validate(): StatusCode {
        if (userName.isEmpty()) return StatusCode.AUTH_INVALID_NAME
        if (password.length < 6) return StatusCode.AUTH_PASSWORD_TOO_SHORT
//        if (password.length > 12) return StatusCode.PasswordMaxLength

        return StatusCode.OK
    }
}