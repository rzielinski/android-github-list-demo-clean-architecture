package com.dreddi.android.githublist.presentation.di.modules

import android.app.Application
import com.dreddi.android.githublist.presentation.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return app
    }
}