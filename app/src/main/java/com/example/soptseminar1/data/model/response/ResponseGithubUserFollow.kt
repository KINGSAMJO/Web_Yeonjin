package com.example.soptseminar1.data.model.response

import com.google.gson.annotations.SerializedName

data class ResponseGithubUserFollow(
    @SerializedName("login")
    val userId : String,
    val avatar_url : String
)
