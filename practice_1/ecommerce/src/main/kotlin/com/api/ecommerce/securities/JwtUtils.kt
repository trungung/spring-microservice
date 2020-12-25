package com.api.ecommerce.securities

import io.jsonwebtoken.Jwts

import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component

import javax.crypto.SecretKey
import io.jsonwebtoken.UnsupportedJwtException

import io.jsonwebtoken.ExpiredJwtException

import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.api.ecommerce.services.UserDetailsImpl
import org.springframework.security.core.Authentication

internal class JwtUtils {

    companion object {
        val TOKEN_SECRET_KEY = "ECommerceAppSecretKey"
        val TOKEN_EXPIRATION: Long = 3600000 // 1 hour
        val TOKEN_CLAIM_USERNAME = "username"
        val TOKEN_CLAIM_ROLES = "roles"
        val TOKEN_PREFIX = "Bearer"
        // creates a spec-compliant secure-random key:
        val jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256) //or HS384 or HS512
        private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

        fun generateJwtToken(authentication: Authentication): String {
            val userPrincipal = authentication.getPrincipal() as UserDetailsImpl
            return Jwts.builder()
                .setSubject(userPrincipal.username)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + TOKEN_EXPIRATION))
                .signWith(jwtSecret)
                .compact()
        }

        fun generateToken(userId: Long, username: String, userRole: String): String {
            val now = Date()
            val exp = Date(now.time + TOKEN_EXPIRATION)

            val jwtToken = Jwts.builder()
                .setSubject(userId.toString())
                .claim(TOKEN_CLAIM_USERNAME, username)
                .claim(TOKEN_CLAIM_ROLES, userRole)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(jwtSecret)
                .compact()
            return "$TOKEN_PREFIX $jwtToken"
        }

        fun parseToken(token: String): Claims {
            return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
        }

        fun getUserNameFromJwtToken(token: String): String {
            return Jwts.parserBuilder()
                .setSigningKey(jwtSecret).build().parseClaimsJws(token).body.subject
        }

        fun validateJwtToken(authToken: String?): Boolean {
            try {
                Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build().parseClaimsJws(authToken)
                return true
            } catch (e: SignatureException) {
                logger.error("Invalid JWT signature: {}", e.message)
            } catch (e: MalformedJwtException) {
                logger.error("Invalid JWT token: {}", e.message)
            } catch (e: ExpiredJwtException) {
                logger.error("JWT token is expired: {}", e.message)
            } catch (e: UnsupportedJwtException) {
                logger.error("JWT token is unsupported: {}", e.message)
            } catch (e: IllegalArgumentException) {
                logger.error("JWT claims string is empty: {}", e.message)
            }
            return false
        }
    }
}