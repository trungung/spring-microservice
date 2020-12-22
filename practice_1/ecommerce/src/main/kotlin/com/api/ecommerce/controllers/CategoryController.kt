package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @GetMapping("")
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll().distinct()
    }
}