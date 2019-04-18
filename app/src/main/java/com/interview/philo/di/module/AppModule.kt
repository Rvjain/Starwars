package com.interview.philo.di.module

import android.content.Context
import com.interview.philo.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: App): Context {
        return application.applicationContext
    }
}
