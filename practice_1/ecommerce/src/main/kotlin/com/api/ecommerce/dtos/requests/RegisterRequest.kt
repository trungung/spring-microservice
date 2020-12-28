package com.api.ecommerce.dtos.requests

import com.api.ecommerce.errors.StatusCode
import java.util.regex.Pattern

class RegisterRequest(
    val userName: String,
    val password: String,
    val email: String,
    val phone: String
) {
    fun validate(): StatusCode {

        if (userName.isEmpty()) return StatusCode.AUTH_INVALID_NAME
        if (email.isEmpty()) return StatusCode.AUTH_EMAIL_NULL_OR_EMPTY
        if (!isEmailValid(email)) return StatusCode.AUTH_EMAIL_INVALID
        if (password.length < 6) return StatusCode.AUTH_PASSWORD_TOO_SHORT
        if (password.length > 12) return StatusCode.AUTH_PASSWORD_TOO_LONG

        return StatusCode.OK
    }

    private fun isEmailValid(email: String): Boolean {
        return EMAIL_PATTERN.matcher(email).matches()
    }

    private val EMAIL_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}