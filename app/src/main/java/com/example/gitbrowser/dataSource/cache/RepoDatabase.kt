package com.example.gitbrowser.dataSource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gitbrowser.dataSource.cache.model.BranchEntity
import com.example.gitbrowser.dataSource.cache.model.RepoEntity

@Database(entities = [RepoEntity::class,BranchEntity::class], version = 2)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        const val DATABASE_NAME: String = "repo_db"
    }
}