package com.example.gitbrowser.dataSource.cache

import androidx.room.*
import com.example.gitbrowser.dataSource.cache.model.RepoEntity
import com.example.gitbrowser.util.Constants.REPO_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: RepoEntity): Long

    @Query("DELETE FROM repos WHERE id =:id")
    suspend fun deleteRepo(id: Int): Int

    @Query("SELECT * FROM repos LIMIT (:page* :pageSize)")
    fun getRepos(page: Int, pageSize: Int = REPO_PAGINATION_PAGE_SIZE): Flow<List<RepoEntity>>
}