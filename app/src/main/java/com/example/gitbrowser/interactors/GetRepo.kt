package com.example.gitbrowser.interactors

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
    fun execute(page: Int): Flow<DataState<List<Repo>>> = flow {
        try {
            emit(DataState.loading())
            repoDao.getRepos(page).collect {
                emit(
                    DataState.success(
                        entityMapper.fromEntityList(it)
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}