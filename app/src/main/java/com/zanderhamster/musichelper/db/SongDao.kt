package com.zanderhamster.musichelper.db

import android.arch.persistence.room.*

@Dao
abstract class SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(song: SongEntity)

    @Query("select * from song")
    abstract fun getAllSongs(): List<SongEntity>

//    @Query("SELECT DISTINCT  * FROM song WHERE name LIKE :arg0 OR artist LIKE :arg0")
//    abstract fun getSong(search: String): SongEntity

    @Query("delete from song")
    abstract fun deleteAllSongs()
}
