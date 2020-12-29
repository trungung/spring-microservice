package com.api.ecommerce.services

import com.api.ecommerce.repositories.CategoryRepository
import com.api.ecommerce.repositories.ProductRepository
import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 * Spring-oriented Implementation for {@link ProductService}
 */
@Service
class ProductServiceImpl: ProductService {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    override fun getAllProducts(page: Pageable): Page<Product> {
        return productRepository.findAll(page)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    override fun getAllProducts(category: Category, page: Pageable): Page<Product> {
        return productRepository.findAllByCategoryId(category.id, page)
    }

    @Transactional
    override fun getProductById(id: Long): Optional<Product> {
        return productRepository.findById(id)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    override fun createProduct(name: String, description: String, unit: Int, price: Double, category: Category): Product {
        val product = Product(name, description, unit, price, category)
        return productRepository.save(product)
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Transactional
    override fun updateProduct(product: Product, name: String, description: String, unit: Int, price: Double) {
        product.name = name
        product.description = description
        product.unit = unit
        product.price = price
        productRepository.save(product)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    override fun deleteProduct(product: Product) {
        productRepository.delete(product)
    }
}