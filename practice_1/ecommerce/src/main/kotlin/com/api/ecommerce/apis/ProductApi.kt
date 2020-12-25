package com.api.ecommerce.apis

import com.api.ecommerce.domains.Product
import com.api.ecommerce.dtos.requests.ProductRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RequestMapping("/products")
interface ProductApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    fun retrieveAllProducts(pageable: Pageable): ResponseEntity<Any>

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    fun retrieveProduct(@PathVariable("id") productId : Long): ResponseEntity<Any>

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/filter")
    fun filterProductsByCategoryId(@RequestParam categoryId: Long, pageable: Pageable): ResponseEntity<Any>

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<Any>

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    fun updateProduct(@PathVariable id: Long, @RequestBody request: ProductRequest): ResponseEntity<Product>

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") productId: Long): ResponseEntity<Any>
}