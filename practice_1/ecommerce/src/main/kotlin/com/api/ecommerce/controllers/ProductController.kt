package com.api.ecommerce.controllers

import com.api.ecommerce.apis.ProductApi
import com.api.ecommerce.daos.CategoryRepository
import com.api.ecommerce.domains.Product
import com.api.ecommerce.dtos.requests.ProductRequest
import com.api.ecommerce.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.net.URI

/**
 * The Spring RestController to manage {@link Product}
 */
class ProductController(
    @Autowired
    val productService: ProductService,
    @Autowired
    val categoryRepository: CategoryRepository
): ProductApi {

    override fun retrieveAllProducts(pageable: Pageable): ResponseEntity<Any> {
        // Getting all products in application...
        val products: Page<Product> = productService.getAllProducts(pageable)
        return ResponseEntity.ok(products)
    }

    override fun retrieveProduct(@PathVariable("id") productId : Long): ResponseEntity<Any> {
        // Getting the requiring product
        val product = productService.getProductById(productId)
        if (!product.isPresent) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(product)
    }

    override fun filterProductsByCategoryId(@RequestParam categoryId: Long, pageable: Pageable): ResponseEntity<Any> {
        val category = categoryRepository.findById(categoryId)
        if (!category.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val products = productService.getAllProducts(category.get(), pageable)
        return ResponseEntity.ok(products)
    }

    override fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<Any> {
        val category = categoryRepository.findById(request.categoryId)
        if (!category.isPresent) {
            return ResponseEntity.notFound().build()
        }
        val product = productService.createProduct(request.name, request.description, request.unit, request.price, category.get())
        return ResponseEntity.created(URI("/products/${product.id}")).body(product)
    }

    override fun updateProduct(@PathVariable id: Long, @RequestBody request: ProductRequest): ResponseEntity<Product> {
        val productOptional = productService.getProductById(id)
        if (!productOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val product = productOptional.get()
        productService.updateProduct(product, request.name, request.description, request.unit, request.price)
        return ResponseEntity.accepted().body(product)
    }

    override fun deleteProduct(@PathVariable("id") productId: Long): ResponseEntity<Any> {
        val product = productService.getProductById(productId)
        if (!product.isPresent) {
            return ResponseEntity.notFound().build()
        }
        productService.deleteProduct(product.get())
        return ResponseEntity.noContent().build()
    }
}