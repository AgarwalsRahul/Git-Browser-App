package com.example.gitbrowser.dataSource.network.response

import com.google.gson.annotations.SerializedName

data class BranchCommitDto(
    @SerializedName("sha")
    var sha:String,
    @SerializedName("url")
    var url:String
)