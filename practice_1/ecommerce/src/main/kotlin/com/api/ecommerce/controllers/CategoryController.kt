package com.api.ecommerce.controllers

import com.api.ecommerce.routers.CategoryRouter
import com.api.ecommerce.daos.CategoryRepository
import com.api.ecommerce.domains.Category
import com.api.ecommerce.dtos.requests.CategoryRequest
import com.api.ecommerce.errors.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

/**
 * The Spring RestController to manage {@link Category}
 */
@RestController
class CategoryController(@Autowired val categoryRepository: CategoryRepository): CategoryRouter {

    override fun getAllCategories(): List<Category> {
        return categoryRepository.findAll().distinct()
    }

    override fun getCategory(@PathVariable("id") categoryId : Long): ResponseEntity<Category> {
        val category = categoryRepository.findById(categoryId).get()
        return ResponseEntity.ok(category)
    }

    override fun createCategory(@RequestBody request: CategoryRequest): ResponseEntity<Category> {
        // Create category and save to db
        val category = Category(request.name, request.description)
        categoryRepository.save(category)
        return ResponseEntity.created(URI("/categories/${category.id}")).body(category)
    }

    override fun updateCategory(@PathVariable("id") categoryId : Long, @RequestBody request: CategoryRequest): ResponseEntity<Category> {
        val found = categoryRepository.findById(categoryId)
        if (!found.isPresent) {
            throw ResourceNotFoundException("Not found Category by ID")
        }

        val category = found.get()
        category.name = request.name
        category.description = request.description

        categoryRepository.save(category)
        return ResponseEntity.accepted().body(category)
    }

    override fun deleteCategory(@PathVariable("id") categoryId: Long): ResponseEntity<Any> {
        categoryRepository.deleteById(categoryId)
        return ResponseEntity.noContent().build()
    }
}