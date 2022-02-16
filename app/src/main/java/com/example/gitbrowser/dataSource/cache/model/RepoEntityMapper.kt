package com.example.gitbrowser.dataSource.cache.model

import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.domain.util.DomainMapper

class RepoEntityMapper : DomainMapper<RepoEntity, Repo> {
    override fun mapToDomainModel(model: RepoEntity): Repo {
        return Repo(model.id, model.name, model.description, model.url)
    }

    override fun mapFromDomainModel(domainModel: Repo): RepoEntity {
        return RepoEntity(
            domainModel.id,
            domainModel.name,
            domainModel.description,
            domainModel.url
        )
    }

    fun fromEntityList(initial: List<RepoEntity>): List<Repo> {
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Repo>): List<RepoEntity> {
        return initial.map { mapFromDomainModel(it) }
    }
}