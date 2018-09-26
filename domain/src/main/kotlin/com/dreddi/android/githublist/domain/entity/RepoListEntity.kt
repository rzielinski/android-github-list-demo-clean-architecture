package com.dreddi.android.githublist.domain.entity

data class RepoListEntity(
    val totalCount: Long,
    val isIncompleteResults: Boolean,
    val repoDataItemsList: List<RepoEntity>?
)
