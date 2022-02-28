package com.example.gitbrowser.dataSource.cache.mapper

import com.example.gitbrowser.dataSource.cache.model.BranchEntity
import com.example.gitbrowser.dataSource.cache.relations.RepoWithBranches
import com.example.gitbrowser.domain.model.Branch
import com.example.gitbrowser.domain.model.BranchCommit
import com.example.gitbrowser.domain.util.DomainMapper

class BranchEntityMapper : DomainMapper<BranchEntity, Branch> {
    override fun mapToDomainModel(model: BranchEntity): Branch {
        return Branch(model.id, model.name, BranchCommit(model.sha, model.url))
    }

    override fun mapFromDomainModel(domainModel: Branch): BranchEntity {
        return BranchEntity(
            domainModel.name,
            domainModel.commit.sha,
            domainModel.commit.url,
            domainModel.id!!
        )
    }


    fun fromEntityList(repoWithBranches: RepoWithBranches): List<Branch> {
        return repoWithBranches.branches.map {
            mapToDomainModel(it)
        }.toList()
    }

    fun toEntityList(branches:List<Branch>):List<BranchEntity>{
        return branches.map {
            mapFromDomainModel(it)
        }
    }


}