package com.dreddi.android.githublist.presentation.app

import android.app.Application
import com.dreddi.android.githublist.presentation.di.dagger.components.ApplicationComponent
import com.dreddi.android.githublist.presentation.di.dagger.components.DaggerApplicationComponent
import com.dreddi.android.githublist.presentation.di.dagger.modules.ApplicationModule

class AppDagger : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = buildAppComponent()
    }

    private fun buildAppComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }
}


