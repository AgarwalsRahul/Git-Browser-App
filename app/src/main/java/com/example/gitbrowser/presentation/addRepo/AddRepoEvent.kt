package com.example.gitbrowser.presentation.addRepo

sealed class AddRepoEvent {

    class AddRepoIntoCacheEvent(val owner:String, val repo:String) : AddRepoEvent()
}