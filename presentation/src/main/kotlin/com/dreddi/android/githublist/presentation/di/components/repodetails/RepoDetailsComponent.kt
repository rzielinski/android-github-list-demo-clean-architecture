package com.dreddi.android.githublist.presentation.di.components.repodetails

import com.dreddi.android.githublist.presentation.di.modules.ApplicationModule
import com.dreddi.android.githublist.presentation.di.modules.DataModule
import com.dreddi.android.githublist.presentation.di.modules.InteractorModule
import com.dreddi.android.githublist.presentation.di.modules.repodetails.RepoDetailsModule
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
