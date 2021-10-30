package com.dreddi.android.githublist.presentation.app

import android.app.Application
import com.dreddi.android.githublist.presentation.di.KoinModuleFactory
import org.koin.core.context.startKoin

class AppKoin : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(KoinModuleFactory.create())
        }
    }
}
