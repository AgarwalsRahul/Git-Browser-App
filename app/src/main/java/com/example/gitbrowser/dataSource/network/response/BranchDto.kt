package com.example.gitbrowser.dataSource.network.response

import com.google.gson.annotations.SerializedName

data class BranchDto(
    @SerializedName("name")
    var name:String,
    @SerializedName("commit")
    var commit:BranchCommitDto
)