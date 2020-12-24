package com.api.ecommerce.daos

import com.api.ecommerce.domains.Category
import com.api.ecommerce.domains.Product
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun whenCreateNewProductWithCategory_success() {
        // given
        val category = Category("C", "c")
        val product = Product("P1", "p", 1, 1000.0, category)
        productRepository.save(product)

        // when
        val found = productRepository.findById(product.id)
        val categoryFound = categoryRepository.findById(category.id)

        // then
        Assert.assertTrue(categoryFound.isPresent)
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().id, product.id)
        Assert.assertEquals(found.get().category, product.category)
    }

    @Test
    fun whenFindById_thenReturnItem() {
        // given
        val category = Category("C", "c")
        val product = Product("P1", "p", 1, 1000.0, category)
        productRepository.save(product)

        // when
        val found = productRepository.findById(product.id)

        // then
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().name, product.name)
    }

    @Test
    fun whenUpdateProduct_thenReturnProduct() {
        // given
        val category = Category("C", "c")
        val product = Product("P1", "p", 1, 1000.0, category)
        productRepository.save(product)

        product.name = "xyz"
        productRepository.save(product)

        // when
        val found = productRepository.findById(product.id)

        // then
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().name, product.name)
    }

    @Test
    fun whenDeleteProduct() {
        // given
        val category = Category("C", "c")
        val product = Product("P1", "p", 1, 1000.0, category)
        productRepository.save(product)

        var found = productRepository.findById(product.id)

        Assert.assertTrue(found.isPresent)
        productRepository.deleteById(product.id)

        // when
        found = productRepository.findById(product.id)

        // then
        Assert.assertTrue(!found.isPresent)
    }

    @Test
    fun whenDeleteCategory_thenProductDeleted() {
        // given
        val category = Category("C", "c")
        categoryRepository.save(category)

        val product = Product("P1", "p", 1, 1000.0, category)
        productRepository.save(product)

        var found = productRepository.findById(product.id)

        Assert.assertTrue(found.isPresent)

        // when
        categoryRepository.deleteById(category.id)

        found = productRepository.findById(product.id)

        // then
        Assert.assertTrue(!found.isPresent)
    }
}