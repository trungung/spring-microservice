package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.errors.StatusCode
import com.api.ecommerce.dtos.requests.AuthenticationRequest
import com.api.ecommerce.dtos.requests.RegisterRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.security.crypto.password.PasswordEncoder

import org.springframework.beans.factory.annotation.Autowired

@RunWith(SpringRunner::class)
@SpringBootTest
class AuthenticationControllerTest: BaseControllerTest() {

    @Autowired
    lateinit var encoder: PasswordEncoder

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
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.AUTH_EMAIL_INVALID.code}"))
            .andReturn()
    }

    @Test
    fun customerRegister_invalid_username() {
        val request = RegisterRequest("", "123456789", "invalid", "123456789")

        performPostRequest("/register", request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.AUTH_INVALID_NAME.code}"))
            .andReturn()
    }

    @Test
    fun customerLogin_success() {
        // given
        val pass = "password"
        val user = User("email@gmail.com", "123456789", Role.ADMIN.value)
        user.username = "userName"
        user.password = encoder.encode(pass)

        userRepository.save(user)

        val request = AuthenticationRequest(user.username, pass)
        performPostRequest("/login", request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(request.userName))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }
}