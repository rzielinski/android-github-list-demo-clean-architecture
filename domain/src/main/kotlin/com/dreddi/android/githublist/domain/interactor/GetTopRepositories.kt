package com.dreddi.android.githublist.domain.interactor

import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable

interface GetTopRepositories {
    fun getTopRepositories(): Observable<RepoListEntity>
}