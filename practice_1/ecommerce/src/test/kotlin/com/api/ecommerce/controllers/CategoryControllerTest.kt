package com.api.ecommerce.controllers

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@WebMvcTest(CategoryController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class CategoryControllerTest: BaseControllerTest() {

    @Test
    fun getCategoryRepository() {
    }

    @Test
    fun setCategoryRepository() {
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