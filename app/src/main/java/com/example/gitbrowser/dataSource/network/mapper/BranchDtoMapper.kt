package com.example.gitbrowser.dataSource.network.mapper

import com.example.gitbrowser.dataSource.network.response.BranchDto
import com.example.gitbrowser.domain.model.Branch
import com.example.gitbrowser.domain.util.DomainMapper
import javax.inject.Inject

class BranchDtoMapper @Inject constructor(private val branchCommitDtoMapper: BranchCommitDtoMapper) :
    DomainMapper<BranchDto, Branch> {
    override fun mapToDomainModel(model: BranchDto): Branch {
        return Branch(
            model.name,
            branchCommitDtoMapper.mapToDomainModel(model.commit)
        )
    }

    override fun mapFromDomainModel(domainModel: Branch): BranchDto {
        return BranchDto(
            domainModel.name,
            branchCommitDtoMapper.mapFromDomainModel(domainModel.commit)
        )
    }
}