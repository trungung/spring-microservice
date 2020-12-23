package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Product
import com.api.ecommerce.dto.requests.ProductRequest
import com.api.ecommerce.dto.responses.ProductsSpecifications
import com.api.ecommerce.repositories.CategoryRepository
import com.api.ecommerce.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/products")
class ProductController(
    @Autowired val productRepository: ProductRepository,
    @Autowired val categoryRepository: CategoryRepository) {

    @GetMapping("")
    fun getAllProducts(): List<Product> {
        return productRepository.findAll().distinct()
    }

    @GetMapping("/filter")
    fun filterProductsByCategoryId(@RequestParam ids: List<Long>): ResponseEntity<Any> {
        val products = productRepository.findAll(ProductsSpecifications.hasCategories(ids)).distinct()
        return ResponseEntity.ok(products)
    }


    @GetMapping("/{id}")
    fun getProduct(@PathVariable("id") productId : Long): ResponseEntity<Product> {
        val product = productRepository.findById(productId).get()
        return ResponseEntity.ok(product)
    }

    @PostMapping("")
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<Any> {
        val category = categoryRepository.findById(request.categoryId)
        if (!category.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val product = Product(request.name, request.description, request.unit, request.price, category.get())
        productRepository.save(product)
        return ResponseEntity.created(URI("/products/${product.id}")).body(product)
    }

    @PutMapping("")
    fun updateProduct(@RequestBody request: ProductRequest): ResponseEntity<Product> {
        val category = categoryRepository.findById(request.categoryId)
        if (!category.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val product = Product(request.name, request.description, request.unit, request.price, category.get())
        productRepository.save(product)
        return ResponseEntity.accepted().body(product)
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable("id") productId: Long): ResponseEntity<Any> {
        productRepository.deleteById(productId)
        return ResponseEntity.noContent().build()
    }
}