package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.exceptions.StatusCode
import com.api.ecommerce.dtos.requests.RegisterRequest
import com.api.ecommerce.dtos.responses.DataResponse
import com.api.ecommerce.dtos.responses.RegisterResponse
import com.api.ecommerce.dtos.responses.UserResponse
import com.api.ecommerce.daos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/")
class AuthenController(@Autowired val userRepository: UserRepository) {

    @PostMapping("customers/register")
    fun customerRegister(@RequestBody request: RegisterRequest): ResponseEntity<Any> {

        // Validate request body
        val valid = request.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        // Check email is exist
        val findUser = userRepository.findByEmail(request.email)
        if (findUser != null) {
            val errorResponse = DataResponse<Any>(StatusCode.EmailExist.code)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        // Create admin user and save to db
        val user = User(request.userName, request.email, request.phone, Role.CUSTOMER.value)
        userRepository.save(user)

        // Create Response data
        val response = RegisterResponse(request.userName, request.email, request.phone)
        return ResponseEntity.created(URI("/users/${user.userId}")).body(response)
    }

    @PostMapping("customers/login")
    fun customerLogin(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        // Validate request body
        val valid = request.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        // TODO

        // Create Response data
        val response = UserResponse(request.userName, request.email, request.phone)
        return ResponseEntity.ok(response)
    }
}