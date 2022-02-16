package com.example.gitbrowser.dataSource.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    companion object {
        const val DATABASE_NAME: String = "repo_db"
    }
}