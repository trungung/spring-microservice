package com.api.ecommerce.securities

import com.api.ecommerce.services.UserDetailsImpl
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey


class JwtUtils {

    companion object {
        val TOKEN_SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E="
        val TOKEN_EXPIRATION: Long = 3600000 // 1 hour
        val TOKEN_CLAIM_USERNAME = "username"
        val TOKEN_CLAIM_ROLES = "roles"
        val TOKEN_PREFIX = "Bearer"
        // creates a spec-compliant secure-random key:
        private val jwtSecret: SecretKey = Keys.hmacShaKeyFor(TOKEN_SECRET_KEY.toByteArray(StandardCharsets.UTF_8))
        private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

        fun generateJwtToken(authentication: Authentication): String {
            val userPrincipal = authentication.principal as UserDetailsImpl
            return Jwts.builder()
                .setSubject(userPrincipal.username)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + TOKEN_EXPIRATION))
                .setId(UUID.randomUUID().toString())
//                .signWith(jwtSecret)
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
//                .signWith(jwtSecret)
                .compact()
            return "$TOKEN_PREFIX $jwtToken"
        }

        fun parseToken(token: String): Claims {
            return Jwts.parserBuilder()
//                .setSigningKey(jwtSecret)
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
//                    .setSigningKey(jwtSecret)
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