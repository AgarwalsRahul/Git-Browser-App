package com.example.gitbrowser.domain.model


data class Repo(
    val id:Int,
    val name:String,
    val description:String?,
    val url:String
)