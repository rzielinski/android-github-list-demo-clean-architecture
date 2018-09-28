package com.dreddi.android.githublist.presentation.di.modules.repodetails

import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RepoDetailsModule {

    @Provides
    fun provideRepoDetailsViewModelFactory(): RepoDetailsViewModelFactory {
        return RepoDetailsViewModelFactory()
    }
}