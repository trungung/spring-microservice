package com.api.ecommerce.dto.responses

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.data.jpa.domain.Specification

object ProductsSpecifications {
    fun hasCategories(ids: List<Long>): Specification<Product> {
        return Specification { root, query, cb ->
            root.get<Category>("id").`in`(ids)
        }
    }
}
