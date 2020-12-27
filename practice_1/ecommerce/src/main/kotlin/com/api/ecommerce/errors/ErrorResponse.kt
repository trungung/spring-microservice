package com.api.ecommerce.errors

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "error")
class ErrorResponse(
    //General error message about nature of error
    private val code: Int,
    private val message: String, //Specific errors in API request processing
) {
    // Define error messages
    companion object {
        private const val INVALID_EMAIL = 404
        private const val INVALID_PASS = 404
        private const val INVALID_USERNAME = 404
        private const val RECORD_NOT_FOUND_ERROR = "Record Not Found"
        private const val FAIL_VALIDATE_ERROR = "Validation Failed"
    }
}