package com.api.ecommerce.reactive

import com.api.ecommerce.domains.User
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class UserHandler(var userRepository: UserRepository) {

    fun getAllUsers(request: ServerRequest): Mono<ServerResponse> {
        val users: Flux<User> = userRepository.getAllUsers()
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(users, User::class.java)
    }
}