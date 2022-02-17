package com.example.gitbrowser.di

import com.example.gitbrowser.dataSource.network.RepoService
import com.example.gitbrowser.dataSource.network.mapper.RepoDtoMapper
import com.example.gitbrowser.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRepoMapper(): RepoDtoMapper {
        return RepoDtoMapper()
    }

    @Singleton
    @Provides
    fun provideRepoService(): RepoService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RepoService::class.java)
    }
}