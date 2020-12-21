package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
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
@ActiveProfiles("test")
@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userRepository: UserRepository

    val mapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    fun getAllUsers() {
        Mockito.`when`(userRepository.findAll()).thenReturn(Collections.emptyList())
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getUserById() {
        val user = User()
        Mockito.`when`(userRepository.findById(anyLong())).thenReturn(Optional.of(user))
        mockMvc.perform(
            MockMvcRequestBuilders.get("/users/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createAdminUser_success() {
        val request = UserRequest("abc", "abc@gmail.com", "123456789")
        val json = mapper.writeValueAsString(request)
        val user = User()
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createAdminUser_invalid_request() {
        val json = mapper.writeValueAsString("request")
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest)
            .andReturn()
    }

    @Test
    fun createAdminUser_emptyBody() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
            .andExpect(status().isBadRequest)
            .andReturn()
    }

    @Test
    fun createAdminUser_invalid_email() {
        val request = UserRequest("abc", "email", "123456789")
        val json = mapper.writeValueAsString(request)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest)
            .andReturn()
    }
}