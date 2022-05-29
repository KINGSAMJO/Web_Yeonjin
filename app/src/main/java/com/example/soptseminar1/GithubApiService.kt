package com.example.soptseminar1

import retrofit2.Call
import retrofit2.http.GET

interface GithubApiService {
    @GET("/users/yeoncheong")
    fun fetchGithubUserInformation(
    ): Call<ResponseGithubUserInformation>

    @GET("/users/yeoncheong/followers")
    fun fetchGithubFollowers(
    ): Call<List<ResponseGithubUserFollow>>

    @GET("/users/yeoncheong/repos")
    fun fetchGithubRepos(
    ): Call<List<ResponseGithubUserRepo>>
}