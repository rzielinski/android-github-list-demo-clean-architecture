package com.dreddi.android.githublist.data.entity

import com.google.gson.annotations.SerializedName

data class RepoListData(
        @SerializedName("total_count")
        val totalCount: Long,

        @SerializedName("incomplete_results")
        val isIncompleteResults: Boolean,

        @SerializedName("items")
        val repoDataItemsList: List<RepoData>?
)
