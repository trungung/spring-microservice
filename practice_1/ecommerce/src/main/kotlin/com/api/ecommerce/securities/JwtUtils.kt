package com.api.ecommerce.securities

import io.jsonwebtoken.Jwts

import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import io.jsonwebtoken.security.Keys

import javax.crypto.SecretKey




class JwtUtils {
    companion object {
        val TOKEN_SECRET_KEY = "ECommerceAppSecretKey"
        val TOKEN_EXPIRATION: Long = 3600000 // 1 hour
        val TOKEN_CLAIM_USERNAME = "username"
        val TOKEN_CLAIM_ROLES = "roles"
        val TOKEN_PREFIX = "Bearer"
        // creates a spec-compliant secure-random key:
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS256) //or HS384 or HS512

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
                .signWith(key)
                .compact()
            return "$TOKEN_PREFIX $jwtToken"
        }

        fun parseToken(token: String): Claims {
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .body
        }
    }
}