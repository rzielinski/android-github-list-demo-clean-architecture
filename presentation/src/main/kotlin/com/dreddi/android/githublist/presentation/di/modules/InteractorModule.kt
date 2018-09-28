package com.dreddi.android.githublist.presentation.di.modules

import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideGetTopRepositories(repoRepository: RepoRepository): GetTopRepositories {
        return GetTopRepositories(repoRepository)
    }
}