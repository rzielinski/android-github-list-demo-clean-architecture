package com.dreddi.android.githublist.presentation.di.dagger.modules

import com.dreddi.android.githublist.domain.RepoRepository
import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideGetTopRepositories(repoRepository: RepoRepository): GetTopRepositoriesUseCase {
        return GetTopRepositoriesUseCase(repoRepository)
    }
}