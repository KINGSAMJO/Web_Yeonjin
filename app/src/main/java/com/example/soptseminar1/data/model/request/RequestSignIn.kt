package com.example.soptseminar1.data.model.request

import com.google.gson.annotations.SerializedName

data class RequestSignIn(
    @SerializedName("email")
    val id: String,
    val password: String
)
