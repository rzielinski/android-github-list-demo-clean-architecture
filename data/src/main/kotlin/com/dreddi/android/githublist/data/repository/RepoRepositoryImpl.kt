package com.dreddi.android.githublist.data.repository

import com.dreddi.android.githublist.data.mapper.RepoListDataToEntityMapper
import com.dreddi.android.githublist.data.repository.store.RepoStore
import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.entity.RepoListEntity
import io.reactivex.Observable

class RepoRepositoryImpl(
        var repoStore: RepoStore,
        var repoListDataToEntityMapper: RepoListDataToEntityMapper) : RepoRepository {

    override fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListEntity> {
        return repoStore.getTopRepositories(page, perPage).map { results ->
            repoListDataToEntityMapper.mapFrom(results)
        }
    }
}