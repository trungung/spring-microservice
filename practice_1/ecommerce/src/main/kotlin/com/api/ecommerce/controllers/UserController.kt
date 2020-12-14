package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.domains.createAdminUser
import com.api.ecommerce.domains.createBusinessUser
import com.api.ecommerce.domains.createCustomerUser
import com.api.ecommerce.repositories.UserRepository
import com.api.ecommerce.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @ResponseBody
    @GetMapping("")
    fun getAllUsers(): List<User> {
        return userService.getAllUsers()
    }

    @ResponseBody
    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") userId : Int): User {
        return userService.getUser(userId)
    }

    @ResponseBody
    @PostMapping("")
    fun createAdmin(
        @RequestParam("user_name") userName: String,
        @RequestParam("email") email: String,
        @RequestParam("phone") phone: String
    ): Map<String, Any> {

        // Create admin user and save to db
        val user = createAdminUser(userName, email, phone)
        userRepository.save(user)

        val map = LinkedHashMap<String, Any>()
        map["result"] = "Added"
        return map
    }

    @ResponseBody
    @PostMapping("")
    fun createBusiness(
        @RequestParam("user_name") userName: String,
        @RequestParam("email") email: String,
        @RequestParam("phone") phone: String
    ): Map<String, Any> {

        // Create admin user and save to db
        val user = createBusinessUser(userName, email, phone)
        userRepository.save(user)

        val map = LinkedHashMap<String, Any>()
        map["result"] = "Added"
        return map
    }

    @ResponseBody
    @PostMapping("")
    fun createCustomer(
        @RequestParam("user_name") userName: String,
        @RequestParam("email") email: String,
        @RequestParam("phone") phone: String
    ): Map<String, Any> {

        // Create admin user and save to db
        val user = createCustomerUser(userName, email, phone)
        userRepository.save(user)

        val map = LinkedHashMap<String, Any>()
        map["result"] = "Added"
        return map
    }

    @ResponseBody
    @PutMapping("")
    fun updateUser(
        @RequestParam("user_id") userId: Int,
        @RequestParam("user_name") userName: String
    ): Map<String, Any> {
        // userService.updateUser(userId, userName)
        val map = LinkedHashMap<String, Any>()
        map["result"] = "Updated"
        return map
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") userId: Int): Map<String, Any> {
        userService.deleteUser(userId)
        val map = LinkedHashMap<String, Any>()
        map["result"] = "Deleted"
        return map
    }
}