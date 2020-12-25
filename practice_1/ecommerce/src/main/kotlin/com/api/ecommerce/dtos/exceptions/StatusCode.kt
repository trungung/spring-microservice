package com.api.ecommerce.dtos.exceptions

enum class StatusCode(val code: Int) {

    // Validation User Register
    NameNotNull(1001),
    EmailNotNull(1002),
    EmailInvalid(1003),
    EmailExist(1004),
    PasswordMinLength(1005),
    PasswordMaxLength(1006),


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