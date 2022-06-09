package com.example.soptseminar1.data.api

import com.example.soptseminar1.data.model.request.RequestSignIn
import com.example.soptseminar1.data.model.request.RequestSignUp
import com.example.soptseminar1.data.model.response.ResponseSignIn
import com.example.soptseminar1.data.model.response.ResponseSignUp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SoptService {
    @POST("auth/signin")
    fun postLogin(
        @Body body: RequestSignIn
    ): Call<ResponseSignIn>

    @POST("auth/signup")
    fun postSignUp(
        @Body body: RequestSignUp
    ): Call<ResponseSignUp>
}