package com.example.gitbrowser.presentation.repoDetail


sealed class DetailStateEvent {

    class DeleteRepoEvent(val id: Int) : DetailStateEvent()
}