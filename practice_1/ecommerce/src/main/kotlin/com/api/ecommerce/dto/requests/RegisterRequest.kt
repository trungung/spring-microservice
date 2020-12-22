package com.api.ecommerce.dto.requests

import com.api.ecommerce.dto.exceptions.StatusCode
import java.util.regex.Pattern

class RegisterRequest(
    val userName: String,
    val password: String,
    val email: String,
    val phone: String
) {
    fun validate(): StatusCode {

        if (userName.isEmpty()) return StatusCode.NameNotNull
        if (email.isEmpty()) return StatusCode.EmailNotNull
        if (!isEmailValid(email)) return StatusCode.EmailInvalid
        if (password.length < 6) return StatusCode.PasswordMinLength
        if (password.length > 12) return StatusCode.PasswordMaxLength

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