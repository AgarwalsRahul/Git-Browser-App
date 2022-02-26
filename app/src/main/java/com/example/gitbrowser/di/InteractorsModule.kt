package com.example.gitbrowser.di

import com.example.gitbrowser.dataSource.cache.RepoDao
import com.example.gitbrowser.dataSource.cache.mapper.RepoEntityMapper
import com.example.gitbrowser.dataSource.network.RepoService
import com.example.gitbrowser.dataSource.network.mapper.RepoDtoMapper
import com.example.gitbrowser.interactors.AddRepo
import com.example.gitbrowser.interactors.DeleteRepo
import com.example.gitbrowser.interactors.GetRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun providesAddRepo(
        repoEntityMapper: RepoEntityMapper,
        repoDao: RepoDao,
        repoService: RepoService,
        repoDtoMapper: RepoDtoMapper
    ): AddRepo {
        return AddRepo(repoDao, repoEntityMapper, repoService, repoDtoMapper)
    }

    @ViewModelScoped
    @Provides
    fun provideGetRepo(repoEntityMapper: RepoEntityMapper, repoDao: RepoDao): GetRepo {
        return GetRepo(repoEntityMapper, repoDao)
    }

    @ViewModelScoped
    @Provides
    fun provideDeleteRepo(repoEntityMapper: RepoEntityMapper, repoDao: RepoDao): DeleteRepo {
        return DeleteRepo(repoEntityMapper, repoDao)
    }
}