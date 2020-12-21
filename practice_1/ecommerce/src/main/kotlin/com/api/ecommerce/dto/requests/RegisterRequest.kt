package com.api.ecommerce.dto.requests

import com.api.ecommerce.dto.exceptions.StatusCode
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.regex.Pattern

class RegisterRequest(
    @SerializedName("user_name")
    val userName: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String
) : Serializable {
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