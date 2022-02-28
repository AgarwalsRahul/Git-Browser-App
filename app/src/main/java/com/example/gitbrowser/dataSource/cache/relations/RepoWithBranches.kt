package com.example.gitbrowser.dataSource.cache.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gitbrowser.dataSource.cache.model.BranchEntity
import com.example.gitbrowser.dataSource.cache.model.RepoEntity

data class RepoWithBranches(
    @Embedded var repo: RepoEntity,
    @Relation(parentColumn = "id", entityColumn = "id")
    var branches: List<BranchEntity>
)