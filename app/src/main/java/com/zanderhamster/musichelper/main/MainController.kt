package com.zanderhamster.musichelper.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView

import com.zanderhamster.musichelper.BaseController
import com.zanderhamster.musichelper.MyApplication
import com.zanderhamster.musichelper.R
import com.zanderhamster.musichelper.db.Song
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import java.util.ArrayList

class MainController : BaseController() {

    @BindView(R.id.recycler) lateinit var recycler: RecyclerView
    private val adapter = MainAdapter()

    override fun onViewBound(view: View) {
        super.onViewBound(view)
        configureRecycler()
        addSong("Солнышко в руках", "Демо")

        MyApplication.database?.songDao()?.getAllSongs()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : SingleObserver<List<Song>> {
                    override fun onSuccess(t: List<Song>) {
                        adapter.setItems(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    private fun configureRecycler() {
        recycler.layoutManager = LinearLayoutManager(recycler.context)
        recycler.adapter = adapter
    }

    private fun addSong(name: String, artist: String) {
        val song = Song(0, name, artist)
        Single.fromCallable {
            MyApplication.database?.songDao()?.insert(song)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_main, container, false)
    }
}
