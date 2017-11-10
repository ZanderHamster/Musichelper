package com.zanderhamster.musichelper

import android.app.Application
import android.arch.persistence.room.Room
import com.zanderhamster.musichelper.db.AppDatabase
import com.zanderhamster.musichelper.di.AppComponent
import com.zanderhamster.musichelper.di.DaggerAppComponent

class MHApplication : Application() {
    lateinit var appComponent: AppComponent

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        MHApplication.database = Room.databaseBuilder(this,
                AppDatabase::class.java,
                "MusicHelperDataBase").build()

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }
}