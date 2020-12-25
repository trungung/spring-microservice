package com.api.ecommerce.daos

import com.api.ecommerce.domains.Category
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    fun whenCreateNewCategory() {
        // given
        val category = Category("abc", "abc")
        categoryRepository.save(category)

        // when
        val found = categoryRepository.findById(category.id)
        // then
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().id, category.id)
    }

    @Test
    fun whenFindById_thenReturnItem() {
        // given
        val category = Category("abc", "abc")
        categoryRepository.save(category)

        // when
        val found = categoryRepository.findById(category.id)

        // then
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().name, category.name)
    }

    @Test
    fun whenUpdateCategory_thenReturnCategory() {
        // given
        val category = Category("abc", "abc")
        categoryRepository.save(category)

        category.name = "xyz"
        categoryRepository.save(category)

        // when
        val found = categoryRepository.findById(category.id)

        // then
        Assert.assertTrue(found.isPresent)
        Assert.assertEquals(found.get().name, category.name)
    }

    @Test
    fun whenDeleteCategory() {
        // given
        val category = Category("abc", "abc")
        categoryRepository.save(category)

        var found = categoryRepository.findById(category.id)

        Assert.assertTrue(found.isPresent)
        categoryRepository.delete(category)

        // when
        found = categoryRepository.findById(category.id)

        // then
        Assert.assertTrue(!found.isPresent)
    }
}