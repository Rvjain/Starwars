package com.interview.philo.di.module

import com.interview.philo.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun bindSourceActivity(): MainActivity
}
