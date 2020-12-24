package com.api.ecommerce.daos

import com.api.ecommerce.domains.Role
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
    fun whenCreateNewUser() {
        // given
        val user = User("userName", "email", "123456789", Role.ADMIN.value)
        userRepository.save(user)

        // when
        val found = userRepository.findById(user.userId)
        // then
        assertNotNull(found)
    }

    @Test
    fun whenFindById_thenReturnUser() {
        // given
        val user = User("userName", "email", "123456789", Role.ADMIN.value)
        entityManager.persist<Any>(user)
        entityManager.flush()

        // when
        val found = userRepository.findById(user.userId)

        // then
        assertNotNull(found)
        assertEquals(found.get().userName, user.userName)
    }

    @Test
    fun whenFindByEmail_thenReturnUser() {
        // given
        val user = User("userName", "email", "123456789", Role.ADMIN.value)
        userRepository.save(user)

        // when
        val found = userRepository.findByEmail(user.email)

        // then
        assertTrue(found.isPresent)
        assertEquals(found.get().email, user.email)
    }

    @Test
    fun whenFindByEmail_notExist_thenReturnEmpty() {
        // given
        val user = User("userName", "email", "123456789", Role.ADMIN.value)

        // when
        val found = userRepository.findByEmail(user.email)

        // then
        assertTrue(found.isPresent)
    }


    @Test
    fun whenUpdateUser_thenReturnUser() {
        // TODO
    }

    @Test
    fun whenDeleteUser() {
        // TODO
    }
}