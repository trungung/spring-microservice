package com.api.ecommerce.services

import com.api.ecommerce.domains.Role
import com.api.ecommerce.domains.User

interface UserService {

    fun getAllUsers(): List<User>

    fun getUser(userId: Int): User

    fun createUser(userId: Int, userName: String, email: String, phone: String, role: Role)

    fun updateUser(userId: Int, userName: String, email: String, phone: String)

    fun deleteUser(userId: Int)
}