package com.example.gitbrowser.interactors

import android.util.Log
import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.mapper.BranchEntityMapper
import com.example.gitbrowser.dataSource.network.RepoService
import com.example.gitbrowser.dataSource.network.mapper.BranchDtoMapper
import com.example.gitbrowser.dataSource.network.response.BranchDto
import com.example.gitbrowser.domain.data.DataState
import com.example.gitbrowser.domain.model.Branch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception

class GetBranches(
    private val repoDao: RepoDao,
    private val entityMapper: BranchEntityMapper,
    private val repoService: RepoService,
    private val branchDtoMapper: BranchDtoMapper
) {

    fun execute(repoId: Int, owner: String, repo: String): Flow<DataState<List<Branch>>> = flow {
        emit(DataState.Loading)
        try {
            //Fetch from the database
            val branches = repoDao.getRepoWithBranches(repoId)
            if (branches.branches.isNotEmpty()) {

                emit(DataState.Success<List<Branch>>(branches.branches.map {
                    entityMapper.mapToDomainModel(it)
                }))
            }
            // get from the network
            val results = fetchFromNetwork(owner, repo)
            // cache into database
            results.forEach {
                repoDao.insertBranch(
                    entityMapper.mapFromDomainModel(
                        branchDtoMapper.mapToDomainModel(
                            it
                        )
                    )
                )
            }
            // getting results from cache
            val cacheResults = repoDao.getRepoWithBranches(repoId)
            emit(DataState.Success<List<Branch>>(cacheResults.branches.map {
                entityMapper.mapToDomainModel(it)
            }))
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


    private suspend fun fetchFromNetwork(owner: String, repo: String): List<BranchDto> {
        return repoService.getBranches(owner, repo);
    }
}