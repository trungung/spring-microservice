package com.api.ecommerce.services

import com.api.ecommerce.domains.User

interface UserService {

    fun getAllUsers(): List<User>

    fun getUser(userId: Int): User

    fun createUser(userId: Int, username: String)

    fun updateUser(userId: Int, username: String)

    fun deleteUser(userId: Int)
}