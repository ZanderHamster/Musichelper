package com.zanderhamster.musichelper.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.zanderhamster.musichelper.MHApplication
import com.zanderhamster.musichelper.db.AppDatabase
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppComponent.AppModule::class))
interface AppComponent {

    fun inject(app: MHApplication)

    fun plus(activityModule: ActivityComponent.ActivityModule): ActivityComponent

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
        @BindsInstance
        fun application(app: Application): Builder

    }

    @Module
    class AppModule {

        @Provides
        @Singleton
        fun provideContext(app: Application): Context = app

        @Provides
        @Singleton
        fun provideDatabase(context: Context): AppDatabase
                = Room.databaseBuilder(context, AppDatabase::class.java, "fm-MusicHelperDataBase").build()
    }
}

