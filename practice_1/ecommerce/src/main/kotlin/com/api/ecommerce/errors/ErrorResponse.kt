package com.api.ecommerce.errors

import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "error")
class ErrorResponse(
    //General error message about nature of error
    private val message: String, //Specific errors in API request processing
    private val details: List<String> //Getter and setters
)