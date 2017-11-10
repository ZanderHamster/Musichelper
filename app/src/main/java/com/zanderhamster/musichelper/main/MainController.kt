package com.zanderhamster.musichelper.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView

import com.zanderhamster.musichelper.BaseController
import com.zanderhamster.musichelper.MHApplication
import com.zanderhamster.musichelper.R
import com.zanderhamster.musichelper.db.SongEntity
import com.zanderhamster.musichelper.db.SongModel
import com.zanderhamster.musichelper.db.SongsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainController : BaseController() {

    @BindView(R.id.recyclerMain) lateinit var recycler: RecyclerView

    @Inject lateinit var songsRepository: SongsRepository

    private val adapter = MainAdapter()

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        controllerComponent.inject(this)
        configureRecycler()
//        addSong("Солнышко в руках", "Демо")
//        addSong("Попрошу тебя", "Вирус")

        val disposable = songsRepository
                .getSongsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.setItems(it)
                }
        disposables.add(disposable)

    }

    private fun configureRecycler() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    private fun addSong(name: String, artist: String) {
        val song = SongModel(UUID.randomUUID().toString(), name, artist)
        songsRepository.addSong(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_main, container, false)
    }
}