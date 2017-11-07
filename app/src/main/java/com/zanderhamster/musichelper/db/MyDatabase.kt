package com.zanderhamster.musichelper.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Song::class), version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

}