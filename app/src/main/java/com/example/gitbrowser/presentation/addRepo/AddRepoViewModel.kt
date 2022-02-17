package com.example.gitbrowser.presentation.addRepo

import androidx.lifecycle.*
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.interactors.AddRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddRepoViewModel @Inject constructor(
    private val interactor: AddRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<Repo>>()
    val dataState: LiveData<DataState<Repo>>
        get() = _dataState

    private val _owner = MutableStateFlow("")
    private val _repo = MutableStateFlow("")


    val isAddEnable = combine(_owner, _repo) { owner, repo ->
        val isOwnerValid = validate(owner)
        val isRepoValid = validate(repo)
        return@combine isOwnerValid and isRepoValid
    }


    fun setStateEvent(stateEvent: AddRepoEvent) {
        when (stateEvent) {
            is AddRepoEvent.AddRepoIntoCacheEvent -> {
                interactor.execute(stateEvent.owner, stateEvent.repo).onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
            }
        }
    }

    fun setOwner(owner: String) {
        _owner.update {
            owner
        }
    }

    fun setRepo(repo: String) {
        _repo.update {
            repo
        }
    }

    private fun validate(text: String): Boolean {
        if (text.isEmpty()) {
            return false
        }
        return true
    }


}