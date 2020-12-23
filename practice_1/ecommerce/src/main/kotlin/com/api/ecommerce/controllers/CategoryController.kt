package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User
import com.api.ecommerce.dto.requests.CategoryRequest
import com.api.ecommerce.dto.requests.UserRequest
import com.api.ecommerce.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.LinkedHashMap
import javax.validation.Valid

@RestController
@RequestMapping("/categories")
class CategoryController {
    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @GetMapping("")
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll().distinct()
    }

    @GetMapping("/{id}")
    fun getCategory(@PathVariable("id") categoryId : Long): ResponseEntity<Category> {
        val category = categoryRepository.findById(categoryId).get()
        return ResponseEntity.ok(category)
    }

    @PostMapping("/")
    fun createCategory(@Valid @RequestBody request: CategoryRequest): ResponseEntity<Category>  {
        // Create category and save to db
        val category = Category(request.name, request.description)
        categoryRepository.save(category)
        return ResponseEntity.ok(category)
    }

    @PutMapping("")
    fun updateCategory(@Valid @RequestBody request: CategoryRequest): ResponseEntity<Category> {
        val category = Category(request.name, request.description)
        categoryRepository.save(category)
        return ResponseEntity.ok(category)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable("id") categoryId: Long): Map<String, Any> {
        categoryRepository.deleteById(categoryId)
        val map = LinkedHashMap<String, Any>()
        map["result"] = "Deleted"
        return map
    }
}