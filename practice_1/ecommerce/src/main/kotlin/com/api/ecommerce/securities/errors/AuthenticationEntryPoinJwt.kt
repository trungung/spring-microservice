package com.api.ecommerce.securities.errors

import com.api.ecommerce.errors.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.ietf.jgss.GSSException
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationEntryPointJwt : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = ErrorResponse.Builder()
            .status(GSSException.UNAUTHORIZED)
            .error("Unauthenticated")
            .message("Insufficient authentication details")
            .path(request.requestURI)
            .build()

        response.status = GSSException.UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}
