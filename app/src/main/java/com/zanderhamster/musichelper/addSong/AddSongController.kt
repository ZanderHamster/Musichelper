package com.zanderhamster.musichelper.addSong

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import com.zanderhamster.musichelper.BaseController
import com.zanderhamster.musichelper.R
import com.zanderhamster.musichelper.db.SongModel
import com.zanderhamster.musichelper.db.SongsRepository
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class AddSongController : BaseController() {

    @BindView(R.id.tilAddSongNumber) lateinit var tilAddSongNumber: TextInputLayout
    @BindView(R.id.tilAddSongName) lateinit var tilAddSongName: TextInputLayout
    @BindView(R.id.tilAddSongArtist) lateinit var tilAddSongArtist: TextInputLayout
    @BindView(R.id.addSongName) lateinit var addSongName: TextInputEditText
    @BindView(R.id.addSongNumber) lateinit var addSongNumber: TextInputEditText
    @BindView(R.id.addSongArtist) lateinit var addSongArtist: TextInputEditText
    @BindView(R.id.toolbar_add_song) lateinit var toolbar: Toolbar
    @BindView(R.id.add_song_add_button) lateinit var btnAdd: Button


    @Inject lateinit var songsRepository: SongsRepository

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_add_song, container, false)

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        controllerComponent.inject(this)
        configureToolbar()
        btnAdd.setOnClickListener {
            addSong(addSongNumber.text.toString().toInt(),
                    addSongName.text.toString(),
                    addSongArtist.text.toString())
        }
    }

    private fun addSong(number: Int, name: String, artist: String) {
        val song = SongModel(UUID.randomUUID().toString(), number, name, artist)
        songsRepository.addSong(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : CompletableObserver {
                    override fun onComplete() {
                        router.handleBack()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {

                    }

                })
    }

    private fun configureToolbar() {
        toolbar.setOnMenuItemClickListener {
            router.handleBack()
            true
        }
    }
}