package com.zanderhamster.musichelper.di

import android.app.Activity
import com.zanderhamster.musichelper.MainActivity
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@PerActivity
@Subcomponent(modules = arrayOf(ActivityComponent.ActivityModule::class))
interface ActivityComponent {
    fun plusController(module: ControllerComponent.ControllerModule): ControllerComponent

    @Module
    class ActivityModule(val activity: MainActivity) {
        @Provides
        @PerActivity
        fun provideActivity(): Activity = activity
    }

    fun inject(activity: MainActivity)
}

