package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

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
    fun createUser(
        @RequestParam("user_id") userId: Int,
        @RequestParam("user_name") userName: String
    ): Map<String, Any> {
        userService.createUser(userId, userName)
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
        userService.updateUser(userId, userName)
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