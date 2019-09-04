package com.dreddi.android.githublist.data.repository.store

import com.dreddi.android.githublist.data.entity.RepoListData
import com.dreddi.android.githublist.data.network.GitHubApi
import io.reactivex.Observable

class CloudRepoStore(
        private val api: GitHubApi
) : RepoStore {

    override fun getTopRepositories(page: Int, perPage: Int): Observable<RepoListData> {
        return api.getAndroidTrendingRepoList(page, perPage)
    }
}