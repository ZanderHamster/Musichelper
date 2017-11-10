package com.zanderhamster.musichelper.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "song")
data class SongEntity(
        @PrimaryKey
        val id: String,
        val name: String,
        val artist: String)
