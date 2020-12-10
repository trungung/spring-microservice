package com.api.ecommerce.services

interface SecurityService {

    fun createToken(subject: String, millis: Long): String

    fun getSubject(token: String): String
}