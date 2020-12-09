package com.api.ecommerce.reactive

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.*
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.ipc.netty.http.server.HttpServer

class Server {

    val HOST = "localhost"
    val PORT = 8081

    fun main(args: Array<String>) {
        val server = Server()
        server.startReactorServer()
        println("Press ENTER to exit.")
        System.`in`.read()
    }

    fun startReactorServer() {
//        val route = routingFunction()
//        val httpHandler = toHttpHandler(route)
//        val adapter = ReactorHttpHandlerAdapter(httpHandler)
//        val server = HttpServer.create(HOST, PORT)
//        server.newHandler(adapter).block()
    }

    fun routingFunction(): RouterFunction<ServerResponse> {
        val repository: UserRepository = UserRepositoryImpl()
        val handler = UserHandler(repository)
        return nest(
            path("/user"),
            nest(
                accept(APPLICATION_JSON),
                route(GET("/"), handler::getAllUsers)
                    .andRoute(method(HttpMethod.GET), handler::getAllUsers))
                .andRoute(
                POST("/").and(contentType(APPLICATION_JSON)), handler::getAllUsers
            )
        )
    }
}