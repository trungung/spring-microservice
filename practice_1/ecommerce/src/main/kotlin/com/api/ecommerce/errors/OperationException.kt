package com.api.ecommerce.errors

import org.springframework.http.HttpStatus.*
import org.springframework.web.server.ResponseStatusException

class OperationException : ResponseStatusException {
    constructor() : super(INTERNAL_SERVER_ERROR)
    constructor(reason: String?) : super(INTERNAL_SERVER_ERROR, reason)
    constructor(reason: String?, cause: Throwable?) : super(INTERNAL_SERVER_ERROR, reason, cause)
}
