package com.dreddi.android.githublist.presentation.di.dagger.modules.repolist

import com.dreddi.android.githublist.domain.usecase.GetTopRepositoriesUseCase
import com.dreddi.android.githublist.presentation.repolist.RepoListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RepoListModule {

    @Provides
    fun provideRepoListViewModelFactory(getTopRepositoriesUseCase: GetTopRepositoriesUseCase): RepoListViewModelFactory {
        return RepoListViewModelFactory(getTopRepositoriesUseCase)
    }
}