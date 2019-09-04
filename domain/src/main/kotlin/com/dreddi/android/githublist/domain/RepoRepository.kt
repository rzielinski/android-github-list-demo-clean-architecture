package com.dreddi.android.githublist.domain

import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable

interface RepoRepository {

    fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListEntity>
}