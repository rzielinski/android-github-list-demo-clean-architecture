package com.dreddi.android.githublist.presentation.di.dagger.components.repodetails

import com.dreddi.android.githublist.presentation.di.dagger.modules.ApplicationModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.DataModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.InteractorModule
import com.dreddi.android.githublist.presentation.di.dagger.modules.repodetails.RepoDetailsModule
import com.dreddi.android.githublist.presentation.repodetails.RepoDetailsFragment

import dagger.Component

@Component(modules = [
    ApplicationModule::class,
    InteractorModule::class,
    DataModule::class,
    RepoDetailsModule::class]
)
interface RepoDetailsComponent {

    fun inject(repoDetailsFragment: RepoDetailsFragment)
}
