package com.dreddi.android.githublist.data.repository.store

import com.dreddi.android.githublist.data.entity.RepoListData
import io.reactivex.Observable

interface RepoStore {

    fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListData>
}