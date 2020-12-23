package com.api.ecommerce.controllers

import com.api.ecommerce.dto.requests.CategoryRequest
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
class CategoryControllerTest: BaseControllerTest() {

    @Test
    fun createCategory_success() {
        val request = CategoryRequest("abc", "abc")
        performPostRequest("/categories", request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getAllCategories() {
    }

    @Test
    fun getCategory() {
    }

    @Test
    fun createCategory() {
    }

    @Test
    fun updateCategory() {
    }

    @Test
    fun deleteCategory() {
    }
}