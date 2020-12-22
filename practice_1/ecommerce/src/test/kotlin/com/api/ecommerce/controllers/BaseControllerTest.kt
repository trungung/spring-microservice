package com.api.ecommerce.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc


@ActiveProfiles("test")
open class BaseControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val mapper = jacksonObjectMapper()

    fun performTest() {

    }

    @Throws(JsonProcessingException::class)
    fun toJson(data: Any): String {
        return mapper.writeValueAsString(data)
    }
}