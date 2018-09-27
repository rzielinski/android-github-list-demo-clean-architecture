package com.dreddi.android.githublist.data.mapper

import com.dreddi.android.githublist.data.entity.RepoListData
import com.dreddi.android.githublist.domain.entity.RepoListEntity

class RepoListDataToEntityMapper(
        var repoDataToEntityMapper: RepoDataToEntityMapper) {

    fun mapFrom(repoListData: RepoListData): RepoListEntity {
        var repoList = repoListData.repoDataItemsList?.map {
            repoDataToEntityMapper.mapFrom(it)
        }
        return RepoListEntity(
                repoListData.totalCount,
                repoListData.isIncompleteResults,
                repoList
        )
    }
}