package com.api.ecommerce.daos

import com.api.ecommerce.domains.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data JPA Repository for {@link Category} entity
 */
@Repository
interface CategoryRepository: CrudRepository<Category, Long> {

}