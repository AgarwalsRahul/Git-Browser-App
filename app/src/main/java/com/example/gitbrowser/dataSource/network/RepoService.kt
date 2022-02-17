package com.example.gitbrowser.dataSource.network

import com.example.gitbrowser.dataSource.network.response.BranchDto
import com.example.gitbrowser.dataSource.network.response.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoService {

    @GET("{owner}/{repo}")
    suspend fun getRepo(@Path("owner") owner: String, @Path("repo") repo: String): RepoDto

    @GET("{owner}/{repo}/branches")
    suspend fun getBranches(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<BranchDto>
}