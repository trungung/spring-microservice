package com.api.ecommerce.repositories

import com.api.ecommerce.domains.User
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager


@RunWith(SpringRunner::class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun whenFindById_thenReturnUser() {
        // given
        val user = User()
        user.userName = "userName"
        user.email = "email"
        user.phone = "123456789"
        user.setAdminRole()
        entityManager.persist<Any>(user)
        entityManager.flush()

        // when
        val found = userRepository.findById(user.userId)

        // then
        assertNotNull(found)
        assertEquals(found.get().userName, user.userName)
    }
}