package com.api.ecommerce.controllers

import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.repositories.UserRepository
import com.google.gson.Gson
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userRepository: UserRepository

    @Test
    fun create_admin_user_success() {
        val request = UserRequest("abc", "abc@gmail.com", "123456789")
        val user = User()
        Mockito.`when`(userRepository.save(any(User::class.java))).thenReturn(user)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/admin")
                .content(request.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(Gson().toJson(request)))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }
}