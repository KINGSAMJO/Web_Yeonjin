package com.example.soptseminar1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("/users/{userId}")
    fun fetchGithubUserInformation(
        @Path("userId") userId: String
    ): Call<ResponseGithubUserInformation>

    @GET("/users/{userId}/followers")
    fun fetchGithubFollowers(
        @Path("userId") userId: String
    ): Call<List<ResponseGithubUserFollow>>

    @GET("/users/{userId}/following")
    fun fetchGithubFollowing(
        @Path("userId") userId: String
    ): Call<List<ResponseGithubUserFollow>>
}