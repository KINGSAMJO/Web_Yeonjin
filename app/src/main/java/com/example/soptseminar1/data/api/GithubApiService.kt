package com.example.soptseminar1.data.api

import com.example.soptseminar1.data.model.response.ResponseGithubUserFollow
import com.example.soptseminar1.data.model.response.ResponseGithubUserInformation
import com.example.soptseminar1.data.model.response.ResponseGithubUserRepo
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