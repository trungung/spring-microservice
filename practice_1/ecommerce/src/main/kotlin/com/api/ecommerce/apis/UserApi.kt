package com.api.ecommerce.apis

import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.requests.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
interface UserApi {
    @GetMapping("")
    fun getAllUsers(): List<User>

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") userId : Long): ResponseEntity<User>

    @PostMapping("/admin")
    fun createAdmin(@RequestBody request: UserRequest): ResponseEntity<User>

    @PostMapping("/business")
    fun createBusiness(@RequestBody request: UserRequest): ResponseEntity<User>

    @PostMapping("/customer")
    fun createCustomer(@RequestBody request: UserRequest): ResponseEntity<User>

    @PutMapping("")
    fun updateUser(@RequestBody request: UserRequest): ResponseEntity<User>

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Long): ResponseEntity<Any>
}