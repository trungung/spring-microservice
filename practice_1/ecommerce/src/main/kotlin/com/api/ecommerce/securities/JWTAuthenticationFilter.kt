package com.api.ecommerce.securities

import com.api.ecommerce.services.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * JWT Authentication Filter
 * If an 'Authorization' header is found in the HTTP Request then the token is being retrieved.
 * That token will be validated and, if successful, then user will be authenticated.
 */
@Component
class JWTAuthenticationFilter: OncePerRequestFilter() {

    private val HEADER_STRING = "Authorization"

    @Autowired
    lateinit var securityService: SecurityService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = request.getHeader(HEADER_STRING)

        if (SecurityContextHolder.getContext().authentication == null) {
            try {
                token?.let { securityService.authenticate(it) }
            } catch (e: Exception) {
                logger.debug("Failed when authenticating token ${token}. Error: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }
}