package com.api.ecommerce.controllers

import com.api.ecommerce.apis.AuthenticationApi
import com.api.ecommerce.daos.UserRepository
import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.exceptions.StatusCode
import com.api.ecommerce.dtos.requests.AuthenticationRequest
import com.api.ecommerce.dtos.requests.RegisterRequest
import com.api.ecommerce.dtos.responses.AuthenticationResponse
import com.api.ecommerce.dtos.responses.DataResponse
import com.api.ecommerce.dtos.responses.RegisterResponse
import com.api.ecommerce.services.SecurityService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

/**
 * The Spring RestController to manage for Authentication
 */
@RestController
class AuthenticationController(
    @Autowired val userRepository: UserRepository,
    @Autowired val securityService: SecurityService
    ): AuthenticationApi {

    private val logger: Logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    override fun customerRegister(@RequestBody request: RegisterRequest): ResponseEntity<Any> {

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
        val user = User(request.email, request.phone, Role.CUSTOMER.value)
        user.username = request.userName
        user.password = request.password
        userRepository.save(user)

        // Create Response data
        val response = RegisterResponse(request.userName, request.email, request.phone)
        return ResponseEntity.created(URI("/users/${user.userId}")).body(response)
    }

    override fun customerLogin(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        // Validate request body
        val valid = request.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.badRequest().body(errorResponse)
        }

       val token = securityService.authenticate(request.userName, request.password)
        // Create Response data
        val response = AuthenticationResponse(request.userName, token)
        return ResponseEntity.ok(response)
    }

    override fun login(@RequestBody credential: AuthenticationRequest): ResponseEntity<Any> {
        // Validate request body
        val valid = credential.validate()
        if (valid != StatusCode.OK) {
            val errorResponse = DataResponse<Any>(valid.code)
            return ResponseEntity.badRequest().body(errorResponse)
        }

        val username: String = credential.userName
        val password: String = credential.password

        // Authenticating...
        val token = securityService.authenticate(username, password)
        logger.debug("User '{}' authenticated successfully -> Token: '{}'", username, token)
        return ResponseEntity.ok(AuthenticationResponse(username, token))
    }
}