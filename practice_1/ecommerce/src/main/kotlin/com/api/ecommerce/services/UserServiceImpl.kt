package com.api.ecommerce.services

import com.api.ecommerce.domains.*
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    // Dummy users
    private val users = arrayListOf(
        createAdminUser("", "", ""),
        createBusinessUser("", "", ""),
        createBusinessUser("", "", ""),
        createCustomerUser("", "", ""),
        createCustomerUser("", "", ""),
        createCustomerUser("", "", ""),
        createCustomerUser("", "", ""),
    )

    override fun getAllUsers(): List<User> {
        return this.users
    }

    override fun getUser(userId: Int): User {
        return this.users.stream()
            .filter { it.userId == userId }
            .findAny()
            .orElse(null)
    }

    override fun createUser(userId: Int, userName: String, email: String, phone: String, role: Role) {
        val user = User(userId, userName, email, phone, role)
        this.users.add(user)
    }

    override fun updateUser(userId: Int, userName: String, email: String, phone: String) {
        users.stream()
            .filter { it.userId == userId }
            .findAny()
            .orElseThrow { RuntimeException("Item Not Found") }
            .let {
                it.userName = userName
                it.email = email
                it.phone = phone
            }
    }

    override fun deleteUser(userId: Int) {
        users.removeIf { it.userId == userId }
    }
}