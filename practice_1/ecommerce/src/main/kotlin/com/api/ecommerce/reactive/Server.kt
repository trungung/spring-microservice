package com.api.ecommerce.reactive

import com.sun.net.httpserver.HttpServer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.servlet.function.RequestPredicates.contentType
import org.springframework.web.servlet.function.RequestPredicates.path
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.nest
import org.springframework.web.servlet.function.ServerResponse


class Server {

    val HOST = "localhost"
    val PORT = 8081

    fun main(args: Array<String>) {
        val server = Server()
        server.startReactorServer()
        System.out.println("Press ENTER to exit.")
        System.`in`.read()
    }

    fun startReactorServer() {
        val route = routingFunction()
        val httpHandler = toHttpHandller(route)
        val adapter = ReactorHttpHandlerAdapter(httpHandler)
        val server = HttpServer.create(HOST, PORT)
        server.newHandler(adapter).block();
    }

    fun routingFunction(): RouterFunction<ServerResponse> {
        val repository: UserRepository = UserRepositorySample()
        val handler = UserHandler(repository)

        return nest(
            path("/user"),
            nest(
                accept(APPLICATION_JSON),
                route(GET("/{id}"), handler::getAllUsers).andRoute(
                    method(HttpMethod.GET),
                    handler::getAllUsers
                )
            ).andRoute(
                POST("/").and(contentType(APPLICATION_JSON)),
                handler::getAllUsers
            )
        )
    }
}