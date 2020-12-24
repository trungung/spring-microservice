package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.dtos.requests.CategoryRequest
import com.api.ecommerce.daos.CategoryRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
class CategoryControllerTest: BaseControllerTest() {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    private val category = Category("C", "c")

    @Test
    fun createCategory_success() {
        val request = CategoryRequest("abc", "abc")
        performPostRequest("/categories", request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getAllCategories() {
        categoryRepository.save(category)
        performGetRequest("/categories")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getCategoryById() {
        categoryRepository.save(category)
        performGetRequest("/categories/${category.id}")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(category.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun updateCategory_success() {
        categoryRepository.save(category)
        category.name = "C2"
        val request = CategoryRequest(category.name, category.description)
        performPutRequest("/categories", request)
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun deleteCategory_success() {
        categoryRepository.save(category)
        performDelete("/categories/${category.id}")
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andReturn()
    }
}