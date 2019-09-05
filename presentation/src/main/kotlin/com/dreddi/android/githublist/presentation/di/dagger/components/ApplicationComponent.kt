package com.dreddi.android.githublist.presentation.di.dagger.components

import com.dreddi.android.githublist.presentation.app.AppDagger
import com.dreddi.android.githublist.presentation.di.dagger.modules.ApplicationModule

import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: AppDagger)
}
