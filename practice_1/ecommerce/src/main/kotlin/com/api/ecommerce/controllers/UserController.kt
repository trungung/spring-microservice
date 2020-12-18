package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.errors.ErrorResponse
import com.api.ecommerce.errors.RecordNotFoundException
import com.api.ecommerce.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.logging.Logger
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import kotlin.jvm.Throws

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepository

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
    fun createAdmin(@RequestBody @Valid request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User()
        user.userName = request.userName
        user.email = request.email
        user.phone = request.phone
        user.setAdminRole()
        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/business")
    fun createBusiness(@RequestBody @Valid request: UserRequest): ResponseEntity<User>  {
        // Create admin user and save to db
        val user = User()
        user.userName = request.userName
        user.email = request.email
        user.phone = request.phone
        user.setBusinessRole()
        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/customer", MediaType.APPLICATION_JSON_VALUE)
    fun createCustomer(@RequestBody @Valid request: UserRequest): ResponseEntity<User> {
        // Create admin user and save to db
        val user = User()
        user.userName = request.userName
        user.email = request.email
        user.phone = request.phone
        user.setCustomerRole()
        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @PutMapping("")
    fun updateUser(@RequestBody request: UserRequest): ResponseEntity<User> {
        val user = User()
        user.userName = request.userName
        user.email = request.email
        user.phone = request.phone
        user.setCustomerRole()
        userRepository.save(user)
        return ResponseEntity.ok(user)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): Map<String, Any> {
        userRepository.deleteById(userId)
        val map = LinkedHashMap<String, Any>()
        map["result"] = "Deleted"
        return map
    }
}