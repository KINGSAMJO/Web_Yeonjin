package com.example.soptseminar1

import com.google.gson.annotations.SerializedName

data class ResponseGithubUserInformation(
    @SerializedName("login")
    val userId : String,
    val name : String,
    val bio : String,
    val avatar_url : String
)
