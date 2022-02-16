package com.example.gitbrowser.dataSource.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitbrowser.dataSource.cache.model.RepoEntity
import com.example.gitbrowser.util.Constants.REPO_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: RepoEntity): Long

    @Query("SELECT * FROM repos LIMIT (:page* :pageSize)")
    fun getRepos(page: Int, pageSize: Int = REPO_PAGINATION_PAGE_SIZE): Flow<List<RepoEntity>>
}