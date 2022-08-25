package com.example.soptseminar1.di

import com.example.soptseminar1.data.api.GithubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubModule {
    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubApiService =
        retrofit.create(GithubApiService::class.java)
}