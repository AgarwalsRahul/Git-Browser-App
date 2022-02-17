package com.example.gitbrowser.dataSource.network.mapper

import com.example.gitbrowser.dataSource.network.response.BranchCommitDto
import com.example.gitbrowser.domain.model.BranchCommit
import com.example.gitbrowser.domain.util.DomainMapper

class BranchCommitDtoMapper: DomainMapper<BranchCommitDto,BranchCommit> {
    override fun mapToDomainModel(model: BranchCommitDto): BranchCommit {
        return BranchCommit(model.sha ,model.url)
    }

    override fun mapFromDomainModel(domainModel: BranchCommit): BranchCommitDto {
        return BranchCommitDto(domainModel.sha ,domainModel.url)
    }
}