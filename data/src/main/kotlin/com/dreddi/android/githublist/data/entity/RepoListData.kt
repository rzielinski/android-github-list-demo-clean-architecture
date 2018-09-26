package com.dreddi.android.githublist.data.entity

import com.google.gson.annotations.SerializedName

class RepoListData {

    @SerializedName("total_count")
    val totalCount: Long = 0

    @SerializedName("incomplete_results")
    val isIncompleteResults: Boolean = false

    @SerializedName("items")
    val repoDataItemsList: List<RepoData>? = null
}
