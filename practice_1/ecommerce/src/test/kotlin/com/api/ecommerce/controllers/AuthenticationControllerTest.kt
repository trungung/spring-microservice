package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dtos.exceptions.StatusCode
import com.api.ecommerce.dtos.requests.AuthenticationRequest
import com.api.ecommerce.dtos.requests.RegisterRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
class AuthenticationControllerTest: BaseControllerTest() {

    @Test
    fun customerRegister_success() {
        val request = RegisterRequest("abc", "123456789", "abc@gmail.com", "123456789")
        performPostRequest("/register", request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(request.userName))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun customerRegister_invalid_email() {
        val request = RegisterRequest("abc", "123456789", "invalid", "123456789")
        performPostRequest("/register", request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.EmailInvalid.code}"))
            .andReturn()
    }

    @Test
    fun customerRegister_invalid_username() {
        val request = RegisterRequest("", "123456789", "invalid", "123456789")

        performPostRequest("/register", request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.NameNotNull.code}"))
            .andReturn()
    }

    @Test
    fun customerLogin_success() {
        // given
        val user = User("email@gmail.com", "123456789", Role.ADMIN.value)
        user.username = "userName"
        user.password = "password"

        userRepository.save(user)

        val request = AuthenticationRequest(user.username, user.password)
        performPostRequest("/login", request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(request.userName))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }
}