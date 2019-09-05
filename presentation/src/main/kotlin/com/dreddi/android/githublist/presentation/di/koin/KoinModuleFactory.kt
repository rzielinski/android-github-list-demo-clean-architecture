package com.dreddi.android.githublist.presentation.di.koin

import com.dreddi.android.githublist.BuildConfig
import com.dreddi.android.githublist.data.mapper.RepoDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoListDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoOwnerDataToEntityMapper
import com.dreddi.android.githublist.data.network.GitHubApi
import com.dreddi.android.githublist.data.repository.RepoRepositoryImpl
import com.dreddi.android.githublist.data.repository.store.CloudRepoStore
import com.dreddi.android.githublist.data.repository.store.RepoStore
import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase
import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsViewModel
import com.dreddi.android.githublist.presentation.repolist.RepoListViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KoinModuleFactory {

    fun create() = module {

        viewModel { RepoDetailsViewModel() }
        viewModel { RepoListViewModel( getTopRepositoriesUseCase = get() ) }

        factory { GetTopRepositoriesUseCase( repoRepository = get() ) }

        factory<RepoRepository> {
            RepoRepositoryImpl(
                    repoStore = get(),
                    repoListDataToEntityMapper = get()
            )
        }

        factory { RepoListDataToEntityMapper( repoDataToEntityMapper = get() ) }

        factory { RepoDataToEntityMapper( repoOwnerDataToEntityMapper = get() ) }

        factory { RepoOwnerDataToEntityMapper() }

        factory<RepoStore> { CloudRepoStore( api = get() ) }

        factory<GitHubApi> {
            val client = OkHttpClient.Builder()
                    .connectTimeout(BuildConfig.API_REST_TIMEOUT, TimeUnit.SECONDS)
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .readTimeout(BuildConfig.API_REST_TIMEOUT, TimeUnit.SECONDS).build()
            Retrofit.Builder()
                    .baseUrl(BuildConfig.API_REST_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(GitHubApi::class.java)
        }

    }
}