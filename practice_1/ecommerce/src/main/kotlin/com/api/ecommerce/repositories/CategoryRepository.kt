package com.api.ecommerce.repositories

import com.api.ecommerce.domains.Category
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: CrudRepository<Category, Long> {

}