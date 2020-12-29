package com.api.ecommerce.errors

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.ietf.jgss.GSSException.UNAUTHORIZED
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        logger.error("Unauthorized error: {}", authException.message)
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
        val errorResponse = ErrorResponse.Builder()
            .status(UNAUTHORIZED)
            .error("Unauthenticated")
            .message("Insufficient authentication details")
            .path(request.requestURI)
            .build()

        response.status = UNAUTHORIZED
        response.contentType = APPLICATION_JSON_VALUE
        response.writer.write(jacksonObjectMapper().writeValueAsString(errorResponse))
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthEntryPointJwt::class.java)
    }
}
