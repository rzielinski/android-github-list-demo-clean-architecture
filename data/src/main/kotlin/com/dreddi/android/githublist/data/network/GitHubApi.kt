package com.dreddi.android.githublist.data.network

import com.dreddi.android.githublist.data.entity.RepoListData

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("/search/repositories?q=android+in:name&sort=stars&order=desc")
    fun getAndroidTrendingRepoList(
            @Query("page") page: Int,
            @Query("per_page") perPage: Int): Observable<RepoListData>
}
