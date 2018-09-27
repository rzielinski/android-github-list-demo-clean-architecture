package com.dreddi.android.githublist.domain.interactor

import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable

class GetTopRepositories(
        private var repoRepository: RepoRepository) {

    fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListEntity> {
        return repoRepository.getTopRepositories(page, perPage)
    }
}