package com.api.ecommerce.repositories

import com.api.ecommerce.domains.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: CrudRepository<Product, Long> {

}