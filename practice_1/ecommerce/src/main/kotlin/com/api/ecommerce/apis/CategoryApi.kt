package com.api.ecommerce.apis

import com.api.ecommerce.domains.Category
import com.api.ecommerce.dtos.requests.CategoryRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
interface CategoryApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    fun getAllCategories(): List<Category>

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    fun getCategory(@PathVariable("id") categoryId : Long): ResponseEntity<Category>

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    fun createCategory(@RequestBody request: CategoryRequest): ResponseEntity<Category>

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    fun updateCategory(@RequestBody request: CategoryRequest): ResponseEntity<Category>

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable("id") categoryId: Long): ResponseEntity<Any>
}