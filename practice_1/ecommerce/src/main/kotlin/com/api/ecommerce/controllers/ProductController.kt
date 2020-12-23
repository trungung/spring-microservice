package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import com.api.ecommerce.dto.requests.CategoryRequest
import com.api.ecommerce.dto.requests.ProductRequest
import com.api.ecommerce.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.LinkedHashMap
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductController(@Autowired val productRepository: ProductRepository) {

    @GetMapping("")
    fun getAllProducts(): List<Product> {
        return productRepository.findAll().distinct()
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable("id") productId : Long): ResponseEntity<Product> {
        val product = productRepository.findById(productId).get()
        return ResponseEntity.ok(product)
    }

    @PostMapping("")
    fun createProduct(@Valid @RequestBody request: ProductRequest): ResponseEntity<Product> {
        // Create category and save to db
        val product = Product(request.name, request.description, request.unit, request.price)
        productRepository.save(product)
        return ResponseEntity.created(URI("/products/${product.id}")).body(product)
    }

    @PutMapping("")
    fun updateProduct(@Valid @RequestBody request: ProductRequest): ResponseEntity<Product> {
        val product = Product(request.name, request.description, request.unit, request.price)
        productRepository.save(product)
        return ResponseEntity.accepted().body(product)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") productId: Long): ResponseEntity<Any> {
        productRepository.deleteById(productId)
        return ResponseEntity.noContent().build()
    }
}