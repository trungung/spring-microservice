package com.api.ecommerce.controllers

import com.api.ecommerce.routers.UserRouter
import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.requests.UserRequest
import com.api.ecommerce.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * The Spring RestController to manage User
 *       JUST FOR TESTING AND DEBUG
 */
@RestController
class UserController(@Autowired val userRepository: UserRepository): UserRouter {

    override fun getAllUsers(): List<User> {
        return userRepository.findAll().distinct()
    }

    override fun getUser(@PathVariable("id") userId : Long): ResponseEntity<User> {
        val user = userRepository.findById(userId).get()
        return ResponseEntity.ok(user)
    }

    override fun createAdmin(@RequestBody request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.ADMIN.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    override fun createBusiness(@RequestBody request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.BUSINESS.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    override fun createCustomer(@RequestBody request: UserRequest): ResponseEntity<User> {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.CUSTOMER.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    override fun updateUser(@RequestBody request: UserRequest): ResponseEntity<User> {
        val user = User(request.email, request.phone, Role.CUSTOMER.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.accepted().body(user)
    }

    override fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<Any> {
        userRepository.deleteById(userId)
        return ResponseEntity.noContent().build()
    }
}