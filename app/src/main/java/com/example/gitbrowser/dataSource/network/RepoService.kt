package com.example.gitbrowser.dataSource.network

import com.example.gitbrowser.dataSource.network.response.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoService {

    @GET
    suspend fun getRepo(@Path("owner") owner: String, @Path("repo") repo: String): RepoDto
}