package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@RunWith(SpringRunner::class)
@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest: BaseControllerTest() {

    @MockBean
    lateinit var userRepository: UserRepository

    @Test
    fun getAllUsers() {
        Mockito.`when`(userRepository.findAll()).thenReturn(Collections.emptyList())
        performGetRequest("/users")
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getUserById() {
        val user = User()
        Mockito.`when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        performGetRequest("/users/1")
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createAdminUser_success() {
        val request = UserRequest("abc", "abc@gmail.com", "123456789")
        val user = User()
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(user)
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