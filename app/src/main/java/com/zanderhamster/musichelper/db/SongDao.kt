package com.zanderhamster.musichelper.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAllSongs(): Single<List<Song>>

    @Insert
    fun insert(song: Song)
}