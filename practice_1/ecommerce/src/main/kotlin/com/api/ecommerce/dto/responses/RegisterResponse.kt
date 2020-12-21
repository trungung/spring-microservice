package com.api.ecommerce.dto.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RegisterResponse(
    @SerializedName("user_name")
    val userName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String) : Serializable