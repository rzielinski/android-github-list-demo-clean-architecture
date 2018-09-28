package com.dreddi.android.githublist.presentation.di.components.repolist

import com.dreddi.android.githublist.presentation.di.modules.ApplicationModule
import com.dreddi.android.githublist.presentation.di.modules.DataModule
import com.dreddi.android.githublist.presentation.di.modules.InteractorModule
import com.dreddi.android.githublist.presentation.di.modules.repolist.RepoListModule
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
