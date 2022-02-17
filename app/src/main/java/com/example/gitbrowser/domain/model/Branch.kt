package com.example.gitbrowser.domain.model

data class Branch(
    val name: String,
    val commit: BranchCommit
)