package com.api.ecommerce.repositories

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
        val user = User("email@gmail.com", "123456789", Role.ADMIN.value)
        user.username = "userName"
        user.password = "password"

        userRepository.save(user)

        // when
        val found = userRepository.findById(user.userId)
        // then
        assertNotNull(found)
    }

    @Test
    fun whenFindById_thenReturnUser() {
        // given
        val user = User("email@gmail.com", "123456789", Role.ADMIN.value)
        user.username = "userName"
        user.password = "password"

//        entityManager.persist<Any>(user)
//        entityManager.flush()
        userRepository.save(user)

        // when
        val found = userRepository.findById(user.userId)

        // then
        assertNotNull(found)
        assertEquals(user.username, found.get().username)
    }

    @Test
    fun whenFindByEmail_thenReturnUser() {
        // given
        val user = User("email@gmail.com", "123456789", Role.ADMIN.value)
        user.username = "userName"
        user.password = "password"

        userRepository.save(user)

        // when
        val found = userRepository.findByEmail(user.email)

        // then
        assertNotNull(found)
        assertEquals(user.email, found?.email)
    }

    @Test
    fun whenFindByEmail_notExist_thenReturnEmpty() {
        // when
        val found = userRepository.findByEmail("email@gmail.com")

        // then
        assertNull(found)
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