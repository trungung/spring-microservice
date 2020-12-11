package com.api.ecommerce.services

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.beans.factory.annotation.Autowired

@RunWith(SpringRunner::class)
@SpringBootTest
internal class UserServiceImplTest {

    @Autowired
    lateinit var userSevice: UserService

    @Test
    fun testGetAllUsers() {
        val users = userSevice.getAllUsers()
        assertEquals(3, users.size)
    }

    @Test
    fun testGetUser() {
        val user = userSevice.getUser(1)
        assertTrue(user.username.contains("David"))
    }

    @Test
    fun createUser() {
        // TODO
    }

    @Test
    fun updateUser() {
        // TODO
    }

    @Test
    fun deleteUser() {
        // TODO
    }
}