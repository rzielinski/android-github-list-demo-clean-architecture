package com.dreddi.android.githublist.presentation.di.components

import com.dreddi.android.githublist.presentation.app.App
import com.dreddi.android.githublist.presentation.di.modules.ApplicationModule

import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: App)
}
