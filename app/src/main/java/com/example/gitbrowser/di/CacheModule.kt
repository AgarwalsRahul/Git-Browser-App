package com.example.gitbrowser.di

import androidx.room.Room
import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.RepoDatabase
import com.example.gitbrowser.dataSource.cache.model.RepoEntityMapper
import com.example.gitbrowser.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): RepoDatabase {
        return Room
            .databaseBuilder(app, RepoDatabase::class.java, RepoDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: RepoDatabase): RepoDao{
        return db.repoDao()
    }

    @Singleton
    @Provides
    fun provideCacheRepoMapper(): RepoEntityMapper{
        return RepoEntityMapper()
    }
}