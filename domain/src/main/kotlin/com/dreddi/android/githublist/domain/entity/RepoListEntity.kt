package com.dreddi.android.githublist.domain.entity

import java.io.Serializable

data class RepoListEntity(
    val totalCount: Long,
    val isIncompleteResults: Boolean,
    val repoDataItemsList: List<RepoEntity>?
): Serializable
