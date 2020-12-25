package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.dtos.requests.CategoryRequest
import com.api.ecommerce.daos.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/categories")
class CategoryController(@Autowired val categoryRepository: CategoryRepository) {

    @GetMapping("")
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll().distinct()
    }

    @GetMapping("/{id}")
    fun getCategory(@PathVariable("id") categoryId : Long): ResponseEntity<Category> {
        val category = categoryRepository.findById(categoryId).get()
        return ResponseEntity.ok(category)
    }


    @PostMapping("")
    fun createCategory(@RequestBody request: CategoryRequest): ResponseEntity<Category>  {
        // Create category and save to db
        val category = Category(request.name, request.description)
        categoryRepository.save(category)
        return ResponseEntity.created(URI("/categories/${category.id}")).body(category)
    }

    @PutMapping("")
    fun updateCategory(@RequestBody request: CategoryRequest): ResponseEntity<Category> {
        val category = Category(request.name, request.description)
        categoryRepository.save(category)
        return ResponseEntity.accepted().body(category)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable("id") categoryId: Long): ResponseEntity<Any> {
        categoryRepository.deleteById(categoryId)
        return ResponseEntity.noContent().build()
    }
}