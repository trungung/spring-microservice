package com.api.ecommerce.securities.filters

import com.api.ecommerce.dtos.requests.AuthenticationRequest
import com.api.ecommerce.securities.JwtUtils.Companion.TOKEN_PREFIX
import com.api.ecommerce.securities.JwtUtils.Companion.generateToken
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//class JWTAuthenticationFilter(val authenticationManager: AuthenticationManager) :
//    UsernamePasswordAuthenticationFilter() {
//
//    @Throws(AuthenticationException::class)
//    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
//        return try {
//            val user = jacksonObjectMapper().readValue(request.inputStream, AuthenticationRequest::class.java)
//            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.userName, user.password))
//        } catch (e: IOException) {
//            throw AuthenticationCredentialsNotFoundException("Failed to resolve authentication credentials", e)
//        }
//    }
//
//    @Throws(IOException::class, ServletException::class)
//    override fun successfulAuthentication(
//        request: HttpServletRequest?, response: HttpServletResponse,
//        chain: FilterChain?, authResult: Authentication
//    ) {
//        response.addHeader(
//            AUTH_HEADER_KEY,
//            TOKEN_PREFIX
//        )
//    }
//
//    companion object {
//        const val AUTH_HEADER_KEY = "Authorization"
//    }
//}
