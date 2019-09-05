package com.dreddi.android.githublist.presentation.di.dagger.components.repolist

import com.dreddi.android.githublist.presentation.di.dagger.modules.ApplicationModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.DataModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.InteractorModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.repolist.RepoListModule
import com.dreddi.android.githublist.presentation.repolist.RepoListFragment

import dagger.Component

@Component(modules = [
    ApplicationModule::class,
    InteractorModule::class,
    DataModule::class,
    RepoListModule::class]
)
interface RepoListComponent {

    fun inject(repoListFragment: RepoListFragment)
}
