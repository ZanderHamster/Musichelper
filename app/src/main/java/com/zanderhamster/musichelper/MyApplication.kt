package com.zanderhamster.musichelper

import android.app.Application
import android.arch.persistence.room.Room
import com.zanderhamster.musichelper.db.MyDatabase

class MyApplication : Application() {

    companion object {
        var database: MyDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        MyApplication.database = Room.databaseBuilder(this,
                MyDatabase::class.java,
                "MusicHelperDb").build()
    }
}