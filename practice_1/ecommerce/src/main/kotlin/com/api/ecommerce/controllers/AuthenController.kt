package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.exceptions.StatusCode
import com.api.ecommerce.dto.requests.RegisterRequest
import com.api.ecommerce.dto.responses.DataResponse
import com.api.ecommerce.dto.responses.RegisterResponse
import com.api.ecommerce.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class AuthenController {

    @Autowired
    lateinit var userRepository: UserRepository

    @PostMapping("customer/register")
    fun customerRegister(@RequestBody request: RegisterRequest): ResponseEntity<DataResponse<Any>> {

        // Validate request body
        val valid = request.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.ok(errorResponse)
        }

        // Check email is exist
        val findUser = userRepository.findUserByEmail(request.email)
        if (findUser != null) {
            val errorResponse = DataResponse<Any>(StatusCode.EmailExist.code)
            return ResponseEntity.ok(errorResponse)
        }

        // Create admin user and save to db
        val user = User(request.userName, request.email, request.phone, Role.CUSTOMER.value)
        userRepository.save(user)

        // Create Response data
        val response = RegisterResponse(request.userName, request.email, request.phone)
        return ResponseEntity.ok(DataResponse(HttpStatus.CREATED.value(), response))
    }

    @PostMapping("customer/login")
    fun customerLogin(@RequestBody request: RegisterRequest): ResponseEntity<DataResponse<Any>> {
        // Validate request body
        val valid = request.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.ok(errorResponse)
        }

        // TODO
        
        // Create Response data
        val response = RegisterResponse(request.userName, request.email, request.phone)
        return ResponseEntity.ok(DataResponse(HttpStatus.CREATED.value(), response))
    }
}