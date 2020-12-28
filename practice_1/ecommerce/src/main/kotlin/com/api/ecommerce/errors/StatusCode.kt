package com.api.ecommerce.errors

enum class StatusCode(val code: Int) {

    // Authentication: 1xxx
    AUTH_INVALID_NAME(1401),
    AUTH_EMAIL_NULL_OR_EMPTY(1402),
    AUTH_EMAIL_INVALID(1403),
    AUTH_EMAIL_EXIST(1404),
    AUTH_PASSWORD_TOO_SHORT(1405),
    AUTH_PASSWORD_TOO_LONG(1406),

    // User: 2xxx
    USER_BAD_REQUEST(2400),
    USER_NOT_FOUND(2404),

    // Category: 3xxx

    // Product: 4xxx

    // Server: 5xxx
    RESOURCE_NOT_FOUND(2404),

    // Validation User Register


    // TODO
    Continue(100),
    SwitchingProtocols(101),
    Processing(102),

    OK(200),
    Created(201),
    Accepted(202),
    NonAuthoritativeInformation(203),
    NoContent(204),
    ResetContent(205),
    PartialContent(206),
    MultiStatus(207),
    AlreadyReported(208),
    IMUsed(226),

    Unknown(0)
}