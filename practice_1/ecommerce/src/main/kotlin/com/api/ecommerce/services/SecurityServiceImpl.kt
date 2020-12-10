package com.api.ecommerce.services

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

@Service
class SecurityServiceImpl: SecurityService {

    private val secretKey = "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX"

    override fun createToken(subject: String, millis: Long): String {
        if (millis <= 0) {
            throw RuntimeException("Expiry time must be greater than Zero :[${millis}] ")
        }

        val signatureAlgorithm = SignatureAlgorithm.HS256
        val apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey)
        val signingKey = SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.jcaName)
        val builder = Jwts.builder().setSubject(subject).signWith(signingKey, signatureAlgorithm)
        val nowMillis = System.currentTimeMillis()
        builder.setExpiration(Date(nowMillis + millis))
        return builder.compact()
    }

    override fun getSubject(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
            .build()
            .parseClaimsJws(token).body
        return claims.subject
    }
}