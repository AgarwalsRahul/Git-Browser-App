package com.example.gitbrowser.interactors

import android.util.Log
import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.mapper.RepoEntityMapper
import com.example.gitbrowser.dataSource.network.RepoService
import com.example.gitbrowser.dataSource.network.mapper.RepoDtoMapper
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

class AddRepo(
    private val repoDao: RepoDao,
    private val entityMapper: RepoEntityMapper,
    private val repoService: RepoService,
    private val repoDtoMapper: RepoDtoMapper
) {

    fun execute(owner: String, repo: String) = flow<DataState<Repo>> {
        try {
            emit(DataState.Loading)
            // get repo from network
            val networkRepo = getRepoFromNetwork(owner, repo)
            // insert it into cache
            val result = repoDao.addRepo(entityMapper.mapFromDomainModel(networkRepo))
            if (result < 0) {
                throw Exception("Unable to add repo. Please try again")
            } else {
                emit(DataState.Success(networkRepo))
            }

        } catch (e: HttpException) {
            Log.d("AddRepo", e.message())
            if (e.message().isNullOrEmpty()) emit(
                DataState.Error(
                    "Repo not found."
                )
            ) else emit(DataState.Error(e.message()))
        } catch (e: Exception) {
            emit(DataState.Error(e.message ?: "Unknown Error!!"))
        }

    }


    private suspend fun getRepoFromNetwork(owner: String, repo: String): Repo {
        return repoDtoMapper.mapToDomainModel(repoService.getRepo(owner, repo))
    }
}