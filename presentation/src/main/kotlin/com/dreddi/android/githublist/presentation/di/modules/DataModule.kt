package com.dreddi.android.githublist.presentation.di.modules

import com.dreddi.android.githublist.BuildConfig
import com.dreddi.android.githublist.data.mapper.RepoDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoListDataToEntityMapper
import com.dreddi.android.githublist.data.mapper.RepoOwnerDataToEntityMapper
import com.dreddi.android.githublist.data.network.GitHubApi
import com.dreddi.android.githublist.data.repository.RepoRepositoryImpl
import com.dreddi.android.githublist.data.repository.store.CloudRepoStore
import com.dreddi.android.githublist.data.repository.store.RepoStore
import com.dreddi.android.githublist.domain.RepoRepository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class DataModule {

    @Provides
    fun provideRepoRepository(repoStore: RepoStore, repoListDataToEntityMapper: RepoListDataToEntityMapper): RepoRepository {
        return RepoRepositoryImpl(repoStore, repoListDataToEntityMapper)
    }

    @Provides
    fun provideRepoListDataToEntityMapper(repoDataToEntityMapper: RepoDataToEntityMapper): RepoListDataToEntityMapper {
        return RepoListDataToEntityMapper(repoDataToEntityMapper)
    }

    @Provides
    fun provideRepoDataToEntityMapper(repoOwnerDataToEntityMapper: RepoOwnerDataToEntityMapper): RepoDataToEntityMapper {
        return RepoDataToEntityMapper(repoOwnerDataToEntityMapper)
    }

    @Provides
    fun provideRepoOwnerDataToEntityMapper(): RepoOwnerDataToEntityMapper {
        return RepoOwnerDataToEntityMapper()
    }

    @Provides
    fun provideRepoStore(api: GitHubApi): RepoStore {
        return CloudRepoStore(api)
    }

    @Provides
    fun provideGitHubApi(apiClient: Retrofit): GitHubApi {
        return apiClient.create(GitHubApi::class.java)
    }

    @Provides
    fun provideApiClient(): Retrofit {
        val client = OkHttpClient.Builder()
                .connectTimeout(BuildConfig.API_REST_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .readTimeout(BuildConfig.API_REST_TIMEOUT, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_REST_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
