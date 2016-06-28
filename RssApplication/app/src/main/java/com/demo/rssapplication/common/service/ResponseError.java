package com.demo.rssapplication.common.service;

import retrofit2.Response;

public class ResponseError {

    // Define Errors string
    public final static String ERR_UNKNOWN = "Unknown!!!";

    // Public properties
    public String message;
    public int errorCode;
    public Response response;

    /**
     * Construct ResponseError with error message.
     *
     * @param message The string error message
     */
    public ResponseError(String message) {
        this.message = message;
    }

    /**
     * Construct ResponseError with error message and Network response.
     *
     * @param message  The string error message
     * @param code     The error code
     * @param response The network response object
     */
    public ResponseError(String message, int code, Response response) {
        this.message = message;
        this.errorCode = code;
        this.response = response;
    }

    public static ResponseError build(Throwable error) {

        if (error == null) {
            return new ResponseError(ERR_UNKNOWN);
        }

        return new ResponseError(error.getMessage());
    }
}
