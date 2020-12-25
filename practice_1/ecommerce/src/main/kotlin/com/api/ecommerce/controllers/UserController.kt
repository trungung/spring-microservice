package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.requests.UserRequest
import com.api.ecommerce.daos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(@Autowired val userRepository: UserRepository) {

    @GetMapping("")
    fun getAllUsers(): List<User> {
        return userRepository.findAll().distinct()
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") userId : Long): ResponseEntity<User> {
        val user = userRepository.findById(userId).get()
        return ResponseEntity.ok(user)
    }

    @PostMapping("/admin")
    fun createAdmin(@RequestBody request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.ADMIN.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/business")
    fun createBusiness(@RequestBody request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.BUSINESS.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/customer", MediaType.APPLICATION_JSON_VALUE)
    fun createCustomer(@RequestBody request: UserRequest): ResponseEntity<User> {
        // Create admin user and save to db
        val user = User(request.email, request.phone, Role.CUSTOMER.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PutMapping("")
    fun updateUser(@RequestBody request: UserRequest): ResponseEntity<User> {
        val user = User(request.email, request.phone, Role.CUSTOMER.value)
        user.username = request.userName
        user.password = request.password

        userRepository.save(user)
        return ResponseEntity.accepted().body(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<Any> {
        userRepository.deleteById(userId)
        return ResponseEntity.noContent().build()
    }
}