package com.api.ecommerce.errors

import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity

import org.springframework.validation.ObjectError

import java.util.ArrayList

import org.springframework.web.context.request.WebRequest

import org.springframework.http.HttpHeaders

import org.springframework.web.bind.MethodArgumentNotValidException

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any?> {
        val details: MutableList<String> = ArrayList()
        details.add(ex.localizedMessage)
        val error = ErrorResponse(SERVER_ERROR, details)
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(RecordNotFoundException::class)
    fun handleUserNotFoundException(ex: RecordNotFoundException, request: WebRequest?): ResponseEntity<Any?> {
        val details: MutableList<String> = ArrayList()
        details.add(ex.localizedMessage)
        val error = ErrorResponse(RECORD_NOT_FOUND_ERROR, details)
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val details: MutableList<String> = ArrayList()
        for (error in ex.bindingResult.allErrors) {
            error.defaultMessage?.let { details.add(it) }
        }
        val error = ErrorResponse(FAIL_VALIDATE_ERROR, details)
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }

    // Define error messages
    companion object {
        private const val SERVER_ERROR = "Server Error"
        private const val RECORD_NOT_FOUND_ERROR = "Record Not Found"
        private const val FAIL_VALIDATE_ERROR = "Validation Failed"
    }
}