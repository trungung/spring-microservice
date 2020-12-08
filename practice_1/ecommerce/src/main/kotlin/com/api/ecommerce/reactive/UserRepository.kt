package com.api.ecommerce.reactive

import com.api.ecommerce.domains.User
import reactor.core.publisher.Flux

interface UserRepository {
    fun getAllUsers(): Flux<User?>?
}
