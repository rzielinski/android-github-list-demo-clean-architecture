package com.dreddi.android.githublist.presentation.di.modules.repolist

import com.dreddi.android.githublist.domain.interactor.GetTopRepositories
import com.dreddi.android.githublist.presentation.repolist.RepoListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RepoListModule {

    @Provides
    fun provideRepoListViewModelFactory(getTopRepositories: GetTopRepositories): RepoListViewModelFactory {
        return RepoListViewModelFactory(getTopRepositories)
    }
}