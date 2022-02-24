package com.example.gitbrowser.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Repo(
    val id:Int,
    val name:String,
    val description:String?,
    val url:String
):Parcelable