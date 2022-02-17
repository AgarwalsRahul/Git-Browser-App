package com.example.gitbrowser.presentation.landingScreen

import android.os.Parcelable
import androidx.lifecycle.*
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.interactors.GetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRepo: GetRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<List<Repo>>>()
    val dataState: LiveData<DataState<List<Repo>>>
        get() = _dataState

    private var page = 1;
    var layoutManagerState: Parcelable? = null
    private var isQueryExhausted: Boolean = true;

    fun setStateEvent(mainStateEvent: RepoListEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is RepoListEvent.GetReposEvent -> {
                    getRepo.execute(page,mainStateEvent.shouldDisplayProgressBar)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }

    fun nextPage() {
        if (!isQueryExhausted) {
            clearLayoutManagerState()
            incrementPage()
            setStateEvent(RepoListEvent.GetReposEvent(false))
        }

    }

    private fun incrementPage() {
        page++
    }

    private fun resetPage() {
        page = 1
    }

    fun clearLayoutManagerState() {
        layoutManagerState = null
    }

    fun loadFirstPage() {
        isQueryExhausted = false
        resetPage()
        clearLayoutManagerState()
        setStateEvent(RepoListEvent.GetReposEvent())
    }

    fun getPage() = page


    fun setQueryExhausted(exhausted: Boolean) {
        isQueryExhausted = exhausted
    }

    fun isQueryExhausted() = isQueryExhausted

    fun setPage(page: Int) {
        this.page = page
    }

    fun refreshQuery(){
        setQueryExhausted(false)
        setStateEvent(RepoListEvent.GetReposEvent(false))
    }


}