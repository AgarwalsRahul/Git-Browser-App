package com.example.gitbrowser.domain.model

data class Branch(
    val id:Int?=null,
    val name: String,
    val commit: BranchCommit
)