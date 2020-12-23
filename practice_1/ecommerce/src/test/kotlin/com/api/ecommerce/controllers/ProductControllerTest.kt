package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import com.api.ecommerce.dto.requests.CategoryRequest
import com.api.ecommerce.dto.requests.ProductRequest
import com.api.ecommerce.repositories.CategoryRepository
import com.api.ecommerce.repositories.ProductRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
class ProductControllerTest: BaseControllerTest() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    private val category = Category("C", "c")
    private val product = Product("P1", "p", 1, 1000.0, category)

    @Test
    fun getAllProducts() {
        performGetRequest("/products")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun getProductById() {
        categoryRepository.save(category)
        productRepository.save(product)
        performGetRequest("/products/${product.id}")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createProduct_success() {
        categoryRepository.save(category)
        val request = ProductRequest("abc", "abc", 10, 1000.0, category.id)
        performPostRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createProduct_with_notFound_category() {
        val request = ProductRequest("abc", "abc", 10, 1000.0, category.id)
        performPostRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn()
    }

    @Test
    fun updateProduct_success() {
        categoryRepository.save(category)
        productRepository.save(product)

        product.name = "P2"
        product.unit = 0
        val request = ProductRequest(product.name, product.description, product.unit, product.price, category.id)
        performPutRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.unit").value(request.unit))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun deleteProduct_success() {
        categoryRepository.save(category)
        productRepository.save(product)
        performDelete("/products/${product.id}")
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andReturn()
    }
}