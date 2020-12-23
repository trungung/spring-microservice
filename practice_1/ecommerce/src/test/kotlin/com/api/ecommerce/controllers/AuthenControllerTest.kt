package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.exceptions.StatusCode
import com.api.ecommerce.dto.requests.RegisterRequest
import com.api.ecommerce.repositories.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest(AuthenController::class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenControllerTest: BaseControllerTest() {

    @MockBean
    lateinit var userRepository: UserRepository

    @Test
    fun customerRegister_success() {
        val request = RegisterRequest("abc", "123456789", "abc@gmail.com", "123456789")
        val user = User()
        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(user)
        performPostRequest("/customers/register", request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun customerRegister_invalid_email() {
        val request = RegisterRequest("abc", "123456789", "invalid", "123456789")
        val user = User()
        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(user)
        performPostRequest("/customers/register", request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.EmailInvalid.code}"))
            .andReturn()
    }

    @Test
    fun customerRegister_invalid_username() {
        val request = RegisterRequest("", "123456789", "invalid", "123456789")
        val user = User()
        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(user)
        performPostRequest("/customers/register", request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("${StatusCode.NameNotNull.code}"))
            .andReturn()
    }
}