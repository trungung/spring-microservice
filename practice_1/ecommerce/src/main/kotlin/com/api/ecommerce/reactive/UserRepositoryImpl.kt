package com.api.ecommerce.reactive

import com.api.ecommerce.domains.User
import reactor.core.publisher.Flux

class UserRepositoryImpl: UserRepository {

    // initiate Users Mocking
    private val users: Map<Int, User> = mapOf(
        1 to User(1, "David"),
        2 to User(2, "John"),
        3 to User(3, "Kevin")
    )

    override fun getAllUsers(): Flux<User> {
        return Flux.fromIterable(this.users.values)
    }
}