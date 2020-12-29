package com.api.ecommerce.errors

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.persistence.EntityNotFoundException

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.lang.Exception
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpHeaders

/**
 * Exception Resolver.
 * Unhandled exceptions in Controller will be formatted here.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class RestExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(ResponseStatusException::class)
    fun handleStatusException(ex: ResponseStatusException, request: WebRequest): ResponseEntity<ErrorResponse> {
        logger.error(ex.reason, ex)
        return handleErrorException(ex, request)
    }

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

    @Suppress("UNCHECKED_CAST")
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error(ex.localizedMessage, ex)
        val responseEntity: ResponseEntity<ErrorResponse> = if (!status.isError) {
            handleStatusException(status, ex, request)
        } else if (HttpStatus.INTERNAL_SERVER_ERROR == status) {
            request.setAttribute("javax.servlet.error.exception", ex, 0)
            handleErrorException(ex, request)
        } else {
            handleErrorException(ex, request)
        }

        return responseEntity as ResponseEntity<Any>
    }

    /**
     * Build a custom error exception response
     *      Status code
     *      Message
     *      Path
     * @param statusCode the error code
     * @param ex the error exception
     * @param request api request
     * @return custom response entity
     */
    private fun handleStatusException(statusCode: HttpStatus, ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        return ErrorResponse.Builder()
            .status(statusCode.value())
            .message("Execution halted")
            .path(getPath(request))
            .entity()
    }

    /**
     * Build a custom error exception response
     *      Status code
     *      Message
     *      Path
     * @param ex the error exception
     * @param request api request
     * @return custom response entity
     */
    private fun handleErrorException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
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