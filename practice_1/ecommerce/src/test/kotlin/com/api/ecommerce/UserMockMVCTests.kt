package com.api.ecommerce

import org.hamcrest.Matchers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootApplication
class MockMvcApplication

@WebMvcTest
@RunWith(SpringRunner::class)
class UserMockMVCTests {

    @Autowired
    lateinit var mockMVC: MockMvc

    @Test
    fun testBasicMvc() {
        val result = mockMVC
            .perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().string("ECOMMERCE!"))
            .andReturn()

        val content = result.response.contentAsString
        print("Response: $content")
        assertNotNull("content")
    }

    @Test
    @Throws(Exception::class)
    fun testSingleUser() {
        val result = mockMVC
            .perform(MockMvcRequestBuilders.get("/user/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("userid", Matchers.`is`("1")))
            .andExpect(jsonPath("username", Matchers.`is`("David")))
            .andReturn()

        val content = result.response.contentAsString
        print("Response: $content")
        assertNotNull(content)
    }
}