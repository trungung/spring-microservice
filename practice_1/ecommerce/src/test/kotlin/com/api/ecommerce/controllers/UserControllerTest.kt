package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.requests.UserRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
class UserControllerTest: BaseControllerTest() {

    @Test
    fun getAllUsers() {
        performGetRequest("/users")
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getUserById() {
        val user = User("userName", "email@gmail.com", "123456789", Role.CUSTOMER.value)
        userRepository.save(user)
        performGetRequest("/users/${user.userId}")
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createAdminUser_success() {
        val user = User("userName", "email@gmail.com", "123456789", Role.ADMIN.value)
        val request = UserRequest(user.userName, user.email, user.phone)
        userRepository.save(user)
        performPostRequest("/users/admin", request)
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createAdminUser_invalid_request() {
        performPostRequest("/users/admin", "request")
            .andExpect(status().isBadRequest)
            .andReturn()
    }

    @Test
    fun createAdminUser_emptyBody() {
        performPostRequest("/users/admin", "")
            .andExpect(status().isBadRequest)
            .andReturn()
    }
}