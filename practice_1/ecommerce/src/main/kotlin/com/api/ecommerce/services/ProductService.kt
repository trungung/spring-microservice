package com.api.ecommerce.services

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*


interface ProductService {

    /**
     * Gets all products present in the system.
     * The result is paginated.
     *
     * @param page the page to fetch results from
     * @return the paginated results
     */
    fun getAllProducts(page: Pageable): Page<Product>

    /**
     * Gets all products that are associated with the given category.
     * The association can be either directly or indirectly.
     * Please {@see Product} entity for more details.
     *
     * @param category the category to filter
     * @param page the page to fetch results from
     * @return the paginated results
     */
    fun getAllProducts(category: Category, page: Pageable): Page<Product>

    /**
     * Gets a specific product by looking for its id.
     *
     * @param id the id of the product to look for
     * @return the product (if any)
     */
    fun getProductById(id: Long): Optional<Product>

    /**
     * Creates a product.
     * If the currency is not 'EUR' then a Currency Exchange
     * will be performed.
     *
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @param unit the unit of the product
     * @param category the category of the product
     * @return the new product
     */
    fun createProduct(name: String, description: String, unit: Int, price: Double, category: Category): Product

    /**
     * Updates an existing product.
     * If the currency is not 'EUR' then a Currency Exchange
     * will be performed.
     *
     * @param product the product to update
     * @param name the new product name
     * @param description the new product description
     * @param unit the new product unit
     * @param price the new product price
     */
    fun updateProduct(product: Product, name: String, description: String, unit: Int, price: Double)

    /**
     * Deletes a product in the system.
     *
     * @param product the product to delete
     */
    fun deleteProduct(product: Product)
}