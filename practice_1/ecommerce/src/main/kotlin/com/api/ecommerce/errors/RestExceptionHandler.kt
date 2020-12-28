package com.api.ecommerce.errors

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.persistence.EntityNotFoundException

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.lang.Exception


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFound(
        ex: EntityNotFoundException
    ): ResponseEntity<ErrorResponse> {
        return ErrorResponse.Builder()
            .status(StatusCode.RESOURCE_NOT_FOUND.code)
            .message(ex.message.toString())
            .entity()
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        logger.error(ex.localizedMessage, ex)
        return handleErrorException(ex, request)
    }

    fun handleErrorException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ErrorResponse.Builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Server encountered an error")
            .path(getPath(request))
            .entity()
    }

    private fun getPath(request: WebRequest): String {
        return request.getDescription(false).substring(4)
    }
}