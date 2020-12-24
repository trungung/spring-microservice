package com.api.ecommerce.securities

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.api.ecommerce.services.SecurityService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import java.lang.Exception


/**
 * JWT Authentication Filter
 * If an 'Authorization' header is found in the HTTP Request then the token is being retrieved.
 * That token will be validated and, if successful, then user will be authenticated.
 */
class JWTAuthenticationFilter: OncePerRequestFilter() {

    private val logger: Logger = LoggerFactory.getLogger(JWTAuthenticationFilter::class.java)
    private val HEADER_STRING = "Authorization"

    @Autowired
    lateinit var securityService: SecurityService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String = request.getHeader(HEADER_STRING)

        if (SecurityContextHolder.getContext().authentication == null) {
            try {
                securityService.authenticate(token)
            } catch (e: Exception) {
                logger.debug("Failed when authenticating token ${token}. Error: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)

    }
}