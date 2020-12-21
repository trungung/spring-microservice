package com.api.ecommerce.dto.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class UserRequest(
    @SerializedName("user_name")
    @NotBlank(message = "UserName should be valid")
    val userName: String,

    @SerializedName("email")
    @Email(message = "Email should be valid")
    val email: String,

    @SerializedName("phone")
    @Min(value = 9, message = "Phone number must be equal or greater than 9")
    @Max(value = 12, message = "Phone number must be equal or less than 12")
    val phone: String
) : Serializable