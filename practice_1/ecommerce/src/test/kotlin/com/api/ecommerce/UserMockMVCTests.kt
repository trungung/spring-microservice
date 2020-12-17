package com.api.ecommerce

import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner::class)
class UserMockMVCTests {

    @Autowired
    lateinit var ctx: WebApplicationContext

    lateinit var mockMVC: MockMvc

    @Before
    fun setUp() {
        mockMVC = MockMvcBuilders.webAppContextSetup(this.ctx).build()
    }


    @Test
    fun testBasicMvc() {
        val result = mockMVC
            .perform(
                get("/")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(content().string("ECOMMERCE!"))
            .andReturn()

        val content = result.response.contentAsString
        print("Response: $content")
    }

    @Test
    @Throws(Exception::class)
    fun testSingleUser() {
        val result = mockMVC
            .perform(
                get("/user/0")
                    .accept(APPLICATION_JSON)
                    .contentType(APPLICATION_JSON)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("userid", `is`(0)))
            .andReturn()

        val content = result.response.contentAsString
        print("Response: $content")
    }
}