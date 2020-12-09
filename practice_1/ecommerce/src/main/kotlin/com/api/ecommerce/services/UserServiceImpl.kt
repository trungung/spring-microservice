package com.api.ecommerce.services

import com.api.ecommerce.domains.User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    // Dummy users
    private val users = arrayListOf(
        User(1, "David"),
        User(2, "John"),
        User(3, "Kevin")
    )

    override fun getAllUsers(): List<User> {
        return this.users
    }

    override fun getUser(userId: Int): User {
        return this.users.stream()
            .filter { it.userid == userId }
            .findAny()
            .orElse(User(0, "Not available"))
    }

    override fun createUser(userId: Int, username: String) {
        val user = User(userId, username)
        this.users.add(user)
    }

    override fun updateUser(userId: Int, username: String) {
        users.stream()
            .filter { it.userid == userId }
            .findAny()
            .orElseThrow { RuntimeException("Item Not Found") }
            .let { it.username = username }
    }

    override fun deleteUser(userId: Int) {
        users.removeIf { it.userid == userId }
    }
}