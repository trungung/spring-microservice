package com.api.ecommerce.errors

import org.springframework.http.HttpHeaders

import org.springframework.http.ResponseEntity


class ErrorResponse private constructor(
    // General error message about nature of error
    val status: Int,
    val code: Int,
    val error: String?,
    val message: String?, // Specific errors in API request processing
    val path: String?
) {
    data class Builder(
        var status: Int = 0,
        var code: Int = 0,
        var error: String = "",
        var message: String = "",
        var path: String = ""
    ) {
        fun status(status: Int) = apply { this.status = status }

        fun code(code: Int) = apply { this.code = code }

        fun error(error: String) = apply { this.error = error }

        fun message(message: String) = apply { this.message = message }

        fun path(path: String) = apply { this.path = path }

        fun build() = ErrorResponse(status, code, error, message, path)

        fun entity(): ResponseEntity<ErrorResponse> {
            return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build())
        }
    }
}