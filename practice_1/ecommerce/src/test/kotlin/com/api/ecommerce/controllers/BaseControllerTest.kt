package com.api.ecommerce.controllers

import com.api.ecommerce.daos.UserRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@ActiveProfiles("test")
open class BaseControllerTest {

    @Autowired
    lateinit var ctx: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var userRepository: UserRepository

    @Before
    open fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build()
    }

    private val mapper = jacksonObjectMapper()

    fun performPostRequest(urlTemplate: String, data: Any): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(toJson(data)))
    }

    fun performPutRequest(urlTemplate: String, data: Any): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(toJson(data)))
    }

    fun performDelete(urlTemplate: String): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
    }

    fun performGetRequest(urlTemplate: String): ResultActions {
        return mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
    }

//    fun performGetRequest(urlTemplate: String, requestBody: Any): ResultActions {
//        return mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate).param()
//            .accept(MediaType.APPLICATION_JSON)
//            .contentType(MediaType.APPLICATION_JSON))
//    }

    @Throws(JsonProcessingException::class)
    fun toJson(data: Any): String {
        return mapper.writeValueAsString(data)
    }
}