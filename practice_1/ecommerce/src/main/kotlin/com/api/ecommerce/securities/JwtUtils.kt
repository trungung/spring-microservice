package com.api.ecommerce.securities

import io.jsonwebtoken.Jwts

import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*

class JwtUtils {
    companion object {
        private val TOKEN_SECRET_KEY = "ECommerceAppSecretKey"
        private val TOKEN_PREFIX = "Bearer"
        private val TOKEN_EXPIRATION: Long = 3600000 // 1 hour
        val TOKEN_CLAIM_USERNAME = "username"
        val TOKEN_CLAIM_ROLES = "roles"

        fun generateToken(userId: Long, username: String, userRole: String): String {
            val now = Date()
            val exp = Date(now.getTime() + TOKEN_EXPIRATION)
            val jwtToken = Jwts.builder()
                .setSubject(userId.toString())
                .claim(TOKEN_CLAIM_USERNAME, username)
                .claim(TOKEN_CLAIM_ROLES, userRole)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, TOKEN_SECRET_KEY)
                .compact()
            return "$TOKEN_PREFIX $jwtToken"
        }

        fun parseToken(token: String): Claims {
            return Jwts.parser()
                .setSigningKey(TOKEN_SECRET_KEY)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
        }
    }
}