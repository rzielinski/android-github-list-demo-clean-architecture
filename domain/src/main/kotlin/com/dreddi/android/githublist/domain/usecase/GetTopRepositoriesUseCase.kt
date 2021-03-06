package com.dreddi.android.githublist.domain.usecase

import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable

class GetTopRepositoriesUseCase(
        private val repoRepository: RepoRepository
) {

    fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListEntity> {
        return repoRepository.getTopRepositories(page, perPage)
    }
}