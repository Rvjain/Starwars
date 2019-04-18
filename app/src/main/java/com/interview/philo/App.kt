package com.interview.philo

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import com.interview.philo.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class App: Application(), HasActivityInjector {

    @Inject
    lateinit var mActivityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        // Initialize the component with dagger
        initializeComponent()
    }

    private fun initializeComponent() {
        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = mActivityDispatchingInjector
}