package com.api.ecommerce.repositories

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    fun findAllByNameLike(name: String): List<Product>

    fun findAllByNameLikeAndPriceLessThanEqual(
        name: String,
        price: Double
    ): List<Product>

    fun findAllByCategoryInAndPriceLessThanEqual(
        categories: List<Category>,
        price: Double
    ): List<Product>

    fun findAllByCategoryInAndPriceBetween(
        categories: List<Category>,
        bottom: Double,
        top: Double
    ): List<Product>

    fun findAllByNameLikeAndCategoryIn(
        name: String,
        categories: List<Category>
    ): List<Product>

    fun findAllByNameLikeAndCategoryInAndPriceBetween(
        name: String,
        categories: List<Category>,
        bottom: Double,
        top: Double
    ): List<Product>
}