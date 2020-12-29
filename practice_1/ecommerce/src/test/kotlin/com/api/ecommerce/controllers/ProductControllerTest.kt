package com.api.ecommerce.controllers

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import com.api.ecommerce.dtos.requests.ProductRequest
import com.api.ecommerce.daos.CategoryRepository
import com.api.ecommerce.daos.ProductRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
class ProductControllerTest: BaseControllerTest() {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    private val categories = listOf(
        Category("1", "1"),
        Category("2", "2"),
        Category("3", "3"),
        Category("4", "4")
    )

    private val product = Product("P1", "p", 1, 1000.0)

    private fun setupCategory() {
        categoryRepository.saveAll(categories)
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun filterProductsByCategoryId() {
        setupCategory()
        val categories = categoryRepository.findAll().distinct()
        for (i in 0..10) {
            val product = Product("P_$i", "p", 1, 1000.0, categories[0])
            productRepository.save(product)
        }
        for (i in 0..10) {
            val product = Product("P_$i", "p", 1, 1000.0, categories[1])
            productRepository.save(product)
        }

        mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
                .param("category_id", "${categories[0].id}")
                .param("page", "0")
                .param("size", "1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun getAllProducts() {
        performGetRequest("/products")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun getProductById() {
        productRepository.deleteAll()
        product.category = categories[0]
        productRepository.save(product)
        performGetRequest("/products/${product.id}")
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun createProduct_success() {
        setupCategory()
        val request = ProductRequest("abc", "abc", 10, 1000.0, categories[0].id)
        performPostRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    fun createProduct_failure() {
        setupCategory()
        val request = ProductRequest("abc", "abc", 10, 1000.0, categories[0].id)
        performPostRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun createProduct_with_notFound_category() {
        val request = ProductRequest("abc", "abc", 10, 1000.0, categories[0].id)
        performPostRequest("/products", request)
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["USER"])
    fun updateProduct_success() {
        setupCategory()
        product.category = categories[0]
        productRepository.save(product)

        product.name = "P2"
        product.unit = 0
        val request = ProductRequest(product.name, product.description, product.unit, product.price, categories[0].id)
        performPutRequest("/products/${product.id}", request)
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(request.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.unit").value(request.unit))
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
    }

    @Test
    @WithMockUser(username="user",roles=["ADMIN"])
    fun deleteProduct_success() {
        setupCategory()
        product.category = categories[0]
        productRepository.save(product)
        performDelete("/products/${product.id}")
            .andExpect(MockMvcResultMatchers.status().isNoContent)
            .andReturn()
    }
}