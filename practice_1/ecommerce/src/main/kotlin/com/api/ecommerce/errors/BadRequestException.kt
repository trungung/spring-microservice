package com.api.ecommerce.errors

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class BadRequestException : ResponseStatusException {

    var statusCode: StatusCode = StatusCode.UNKNOW

    constructor(statusCode: StatusCode) : super(HttpStatus.BAD_REQUEST) { this.statusCode = statusCode }
    constructor(statusCode: StatusCode, reason: String) : super(HttpStatus.BAD_REQUEST, reason) { this.statusCode = statusCode }
    constructor(statusCode: StatusCode, reason: String, cause: Throwable) : super(HttpStatus.BAD_REQUEST, reason, cause) { this.statusCode = statusCode }
}
