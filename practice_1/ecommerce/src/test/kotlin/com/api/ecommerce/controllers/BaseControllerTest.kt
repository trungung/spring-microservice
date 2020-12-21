package com.api.ecommerce.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
open class BaseControllerTest {
    @Test
    fun test() {

    }
}