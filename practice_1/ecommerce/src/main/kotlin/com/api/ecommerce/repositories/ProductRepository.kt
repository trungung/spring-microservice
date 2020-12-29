package com.api.ecommerce.repositories

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for {@link Product} entity
 */
@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    fun findAllByCategoryId(categoryId: Long, page: Pageable): Page<Product>

    fun findAllByCategoryIsIn(categories: List<Category>, page: Pageable): List<Product>
}