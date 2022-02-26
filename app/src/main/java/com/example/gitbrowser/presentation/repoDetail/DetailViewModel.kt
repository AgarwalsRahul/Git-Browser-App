package com.example.gitbrowser.presentation.repoDetail

import androidx.lifecycle.*
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.interactors.DeleteRepo
import com.example.gitbrowser.interactors.GetRepo
import com.example.gitbrowser.presentation.landingScreen.RepoListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val deleteRepo: DeleteRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<*>>()
    val dataState: LiveData<DataState<*>>
        get() = _dataState

    fun setStateEvent(stateEvent: DetailStateEvent) {
        viewModelScope.launch {
            when (stateEvent) {
                is DetailStateEvent.DeleteRepoEvent -> {
                    deleteRepo.execute(stateEvent.id)
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }
}