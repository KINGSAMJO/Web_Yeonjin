package com.example.soptseminar1.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubApiCreator {
    private const val BASE_URL = "https://api.github.com"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val githubApiService : GithubApiService = retrofit.create(GithubApiService::class.java)
}