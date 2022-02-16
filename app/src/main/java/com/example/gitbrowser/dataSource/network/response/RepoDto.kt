package com.example.gitbrowser.dataSource.network.response

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String?,
    @SerializedName("html_url")
    val htmlUrl: String
)