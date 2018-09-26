package com.dreddi.android.githublist.domain.interactor

import com.dreddi.android.githublist.domain.entity.RepoListEntity

interface GetTopRepositories {
    fun getTopRepositories(): RepoListEntity
}