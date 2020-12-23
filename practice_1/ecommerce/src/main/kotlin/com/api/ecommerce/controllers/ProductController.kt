package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Product
import com.api.ecommerce.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController {
    @Autowired
    lateinit var productRepository: ProductRepository

    @GetMapping("")
    fun getAllProducts(): List<Product> {
        return productRepository.findAll().distinct()
    }
}