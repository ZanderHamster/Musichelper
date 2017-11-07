package com.zanderhamster.musichelper.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity()
data class Song(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        val name: String = "",
        val artist: String = "") {
    constructor() : this(0, "", "")
}