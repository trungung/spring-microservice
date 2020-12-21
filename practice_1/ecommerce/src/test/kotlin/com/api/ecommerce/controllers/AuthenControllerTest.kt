package com.api.ecommerce.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@WebMvcTest(AuthenController::class)
class AuthenControllerTest: BaseControllerTest() {

    @Test
    fun customerRegister() {
    }
}