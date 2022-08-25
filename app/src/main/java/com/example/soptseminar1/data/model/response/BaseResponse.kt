package com.example.soptseminar1.data.model.response

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val data: T?
)
