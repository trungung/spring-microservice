package com.api.ecommerce

import org.hamcrest.Matchers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner::class)
class UserMockMVCTests {

    @Autowired
    lateinit var ctx: WebApplicationContext

    lateinit var mockMVC: MockMvc

    @Before
    fun setup() {
        this.mockMVC = MockMvcBuilders.webAppContextSetup(this.ctx).build()
    }

    @Test
    fun testBasicMvc() {
        val result = mockMVC
            .perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("result", Matchers.`is`("ECOMMERCE!")))
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