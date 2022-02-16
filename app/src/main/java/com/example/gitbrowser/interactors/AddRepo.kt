package com.example.gitbrowser.interactors

import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.model.RepoEntityMapper
import com.example.gitbrowser.dataSource.network.RepoService
import com.example.gitbrowser.dataSource.network.response.RepoDtoMapper
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Repo
import kotlinx.coroutines.flow.flow
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
            if(result<0){
                throw Exception("Unable to add repo. Please try again")
            }else{
                emit(DataState.Success(networkRepo))
            }

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }


    private suspend fun getRepoFromNetwork(owner: String, repo: String): Repo {
        return repoDtoMapper.mapToDomainModel(repoService.getRepo(owner, repo))
    }
}