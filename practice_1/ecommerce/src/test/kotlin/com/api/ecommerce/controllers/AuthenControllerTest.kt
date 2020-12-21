package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Assert
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(AuthenController::class)
class AuthenControllerTest: BaseControllerTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userRepository: UserRepository

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun customerRegister_success() {
        val request = UserRequest("abc", "abc@gmail.com", "123456789")
        val json = mapper.writeValueAsString(request)
        val user = User()
        Mockito.`when`(userRepository.save(ArgumentMatchers.any(User::class.java))).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }
}