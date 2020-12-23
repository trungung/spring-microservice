package com.api.ecommerce.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders


@ActiveProfiles("test")
open class BaseControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    private val mapper = jacksonObjectMapper()

    fun performPostRequest(urlTemplate: String, data: Any): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(toJson(data)))
    }

    fun performGetRequest(urlTemplate: String): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
    }

    @Throws(JsonProcessingException::class)
    fun toJson(data: Any): String {
        return mapper.writeValueAsString(data)
    }
}