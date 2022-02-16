package com.example.gitbrowser.dataSource.network.response

import com.example.gitbrowser.domain.model.Repo
import com.example.gitbrowser.domain.util.DomainMapper

class RepoDtoMapper : DomainMapper<RepoDto, Repo> {
    override fun mapToDomainModel(model: RepoDto): Repo {
        return Repo(
            model.id,
            model.name,
            model.description,
            model.htmlUrl
        )
    }

    override fun mapFromDomainModel(domainModel: Repo): RepoDto {
        return RepoDto(
            domainModel.id,
            domainModel.name,
            domainModel.description,
            domainModel.url
        )
    }

    fun toDomainList(initial: List<RepoDto>): List<Repo> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Repo>): List<RepoDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}