package com.example.gitbrowser.dataSource.cache

import androidx.room.*
import com.example.gitbrowser.dataSource.cache.model.BranchEntity
import com.example.gitbrowser.dataSource.cache.model.RepoEntity
import com.example.gitbrowser.dataSource.cache.relations.RepoWithBranches
import com.example.gitbrowser.util.Constants.REPO_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: RepoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBranch(branch: BranchEntity): Long

    @Query("DELETE FROM repos WHERE id =:id")
    suspend fun deleteRepo(id: Int): Int

    @Query("SELECT * FROM repos LIMIT (:page* :pageSize)")
    fun getRepos(page: Int, pageSize: Int = REPO_PAGINATION_PAGE_SIZE): Flow<List<RepoEntity>>

    @Transaction
    @Query("SELECT * FROM repos WHERE id = :repoId")
    suspend fun getRepoWithBranches(repoId: Int): RepoWithBranches
}