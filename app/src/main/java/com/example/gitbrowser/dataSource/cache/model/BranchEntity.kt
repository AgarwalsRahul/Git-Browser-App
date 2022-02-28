package com.example.gitbrowser.dataSource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BranchEntity(
    @PrimaryKey(autoGenerate = false)
    var name:String,
    var sha:String,
    var url:String,
    var id:Int
)