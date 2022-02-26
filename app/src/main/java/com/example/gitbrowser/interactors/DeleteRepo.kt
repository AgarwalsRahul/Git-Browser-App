package com.example.gitbrowser.interactors

import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.mapper.RepoEntityMapper
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class DeleteRepo(
    private val entityMapper: RepoEntityMapper,
    private val repoDao: RepoDao
) {
    fun execute(id: Int): Flow<DataState<*>> = flow {
        try {

            emit(DataState.Loading)

            val result = repoDao.deleteRepo(id)
            if (result < 0) {
                throw Exception("Unable to delete repo. Please try again")
            } else {
                emit(DataState.Success(Any()))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "UNKNOWN ERROR!!"))
        }
    }
}