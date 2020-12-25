package com.api.ecommerce.securities

import com.api.ecommerce.daos.UserRepository
import com.api.ecommerce.services.SecurityService
import com.api.ecommerce.services.UserDetailsServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.util.StringUtils


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

    @Autowired
    lateinit var userDetailsService: UserDetailsServiceImpl

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
//        val token: String? = request.getHeader(HEADER_STRING)
//
//        if (SecurityContextHolder.getContext().authentication == null) {
//            try {
//                token?.let { securityService.authenticate(it) }
//            } catch (e: Exception) {
//                logger.debug("Failed when authenticating token ${token}. Error: ${e.message}")
//            }
//        }

        try {
            val jwt: String = parseJwt(request)
            if (jwt != null && JwtUtils.validateJwtToken(jwt)) {
                val username: String = JwtUtils.getUserNameFromJwtToken(jwt)
                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: java.lang.Exception) {
            logger.error("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun parseJwt(request: HttpServletRequest): String {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else ""
    }
}