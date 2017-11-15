package com.zanderhamster.musichelper.db

data class SongModel(
        val songId: String = "",
        val number: Int = 0,
        val name: String = "",
        val artist: String = "",
        val tone: String = ""
) {
    companion object {
        val EMPTY = SongModel()
    }
}