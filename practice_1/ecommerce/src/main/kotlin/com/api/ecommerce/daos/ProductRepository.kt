package com.api.ecommerce.daos

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for {@link Product} entity
 */
@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    fun findAllByCategoryId(categoryId: Long): List<Product>

    fun findAllByCategoryIsIn(categories: List<Category>): List<Product>
}