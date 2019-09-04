package com.dreddi.android.githublist.data.mapper

import com.dreddi.android.githublist.data.entity.RepoListData
import com.dreddi.android.githublist.domain.entity.RepoListEntity

class RepoListDataToEntityMapper(
        private val repoDataToEntityMapper: RepoDataToEntityMapper
) {

    fun mapFrom(repoListData: RepoListData): RepoListEntity {
        val repoList = repoListData.repoDataItemsList?.map {
            repoDataToEntityMapper.mapFrom(it)
        }
        return RepoListEntity(
                repoListData.totalCount,
                repoListData.isIncompleteResults,
                repoList
        )
    }
}