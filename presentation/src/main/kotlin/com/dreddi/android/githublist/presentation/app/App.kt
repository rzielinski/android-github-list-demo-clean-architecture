package com.dreddi.android.githublist.presentation.app

import android.app.Application
import com.dreddi.android.githublist.presentation.di.components.ApplicationComponent
import com.dreddi.android.githublist.presentation.di.components.DaggerApplicationComponent
import com.dreddi.android.githublist.presentation.di.modules.ApplicationModule

class App : Application() {

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


