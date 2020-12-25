package com.api.ecommerce.apis

import com.api.ecommerce.dtos.requests.AuthenticationRequest
import com.api.ecommerce.dtos.requests.RegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/")
interface AuthenticationApi {

    @PostMapping("register")
    fun customerRegister(@RequestBody request: RegisterRequest): ResponseEntity<Any>

    @PostMapping("admin/login")
    fun customerLogin(@RequestBody request: RegisterRequest): ResponseEntity<Any>

    @PostMapping("login")
    fun login(@RequestBody credential: AuthenticationRequest): ResponseEntity<Any>
}