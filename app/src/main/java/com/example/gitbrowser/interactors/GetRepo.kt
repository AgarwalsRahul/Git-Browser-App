package com.example.gitbrowser.interactors

import android.widget.ProgressBar
import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.model.RepoEntityMapper
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetRepo(
    private val entityMapper: RepoEntityMapper,
    private val repoDao: RepoDao
) {
    fun execute(page: Int, shouldDisplayProgressBar: Boolean): Flow<DataState<List<Repo>>> = flow {
        try {
            if (!shouldDisplayProgressBar)
                emit(DataState.Loading)
            repoDao.getRepos(page).collect {
                emit(
                    DataState.Success(
                        entityMapper.fromEntityList(it)
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message?:"UNKNOWN ERROR!!"))
        }
    }
}