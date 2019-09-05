package com.dreddi.android.githublist.presentation.di.dagger.modules

import android.app.Application
import com.dreddi.android.githublist.presentation.app.AppDagger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val appDagger: AppDagger) {

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return appDagger
    }
}