package com.example.gitbrowser.presentation.landingScreen

sealed class RepoListEvent {

    class GetReposEvent(val shouldDisplayProgressBar:Boolean = true) : RepoListEvent()


}