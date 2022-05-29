package com.example.soptseminar1

import com.google.gson.annotations.SerializedName

data class ResponseGithubUserFollow(
    @SerializedName("login")
    val userId : String,
    val avatar_url : String,
    val bio : String
)
