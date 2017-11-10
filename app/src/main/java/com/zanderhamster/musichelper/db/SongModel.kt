package com.zanderhamster.musichelper.db

data class SongModel(
        val songId: String = "",
        val name: String = "",
        val artist: String = ""
) {
    companion object {
        val EMPTY = SongModel()
    }
}