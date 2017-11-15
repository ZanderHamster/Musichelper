package com.zanderhamster.musichelper.main

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import butterknife.BindView
import com.bluelinelabs.conductor.RouterTransaction

import com.zanderhamster.musichelper.BaseController
import com.zanderhamster.musichelper.R
import com.zanderhamster.musichelper.addSong.AddSongController
import com.zanderhamster.musichelper.db.SongModel
import com.zanderhamster.musichelper.db.SongsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MainController : BaseController() {

    @BindView(R.id.recyclerMain) lateinit var recycler: RecyclerView
    @BindView(R.id.toolbar_main) lateinit var toolbar: Toolbar

    @Inject lateinit var songsRepository: SongsRepository

    private val adapter = MainAdapter()

    override fun onViewBound(view: View) {
        super.onViewBound(view)

        controllerComponent.inject(this)
        configureToolbar()
        configureRecycler()

        val disposable = songsRepository
                .getSongsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.setItems(it)
                }
        disposables.add(disposable)

//        webView.loadUrl("file:///android_asset/Power band.htm")
    }

    private fun setCurrentDemoList(): List<SongModel> {
        val items = mutableListOf<SongModel>()
        items.add(SongModel(UUID.randomUUID().toString(), 14, "Highway to hell", "AC/DC", "C038"))
        items.add(SongModel(UUID.randomUUID().toString(), 6, "Твои глаза", "Лобода", "A055"))
        items.add(SongModel(UUID.randomUUID().toString(), 18, "Sweet dreams", "Eurythmics", "B102 +02"))
        items.add(SongModel(UUID.randomUUID().toString(), 8, "Песня 404", "Время и Стекло", "D032 t125"))
        items.add(SongModel(UUID.randomUUID().toString(), 5, "Туманы", "Макс Барских", "A038 SW1 A1-7"))
        items.add(SongModel(UUID.randomUUID().toString(), 20, "Бьет бит", "IOWA", "A034 SW1 A1-4"))
        items.add(SongModel(UUID.randomUUID().toString(), 13, "I kissed a girl", "Katy Parry", "A101"))
        items.add(SongModel(UUID.randomUUID().toString(), 23, "Экспонат", "Ленинград", "A038 SW1 A1-6"))
        items.add(SongModel(UUID.randomUUID().toString(), 24, "Venus", "Shoking Blue", "A038 SW1 A1-6"))
        items.add(SongModel(UUID.randomUUID().toString(), 11, "Он тебя целует", "Руки вверх", "C038 -02"))
        items.add(SongModel(UUID.randomUUID().toString(), 10, "Лейла", "Jah Khalib", "COMBI A000"))

        items.add(SongModel(UUID.randomUUID().toString(), -1, "Блок", "2"))

        items.add(SongModel(UUID.randomUUID().toString(), 4, "Импульсы", "Темникова", "A038 SW1 A1-6"))
        items.add(SongModel(UUID.randomUUID().toString(), 1, "Мало тебя", "Серебро", "A038 B4-2 vol-"))
        items.add(SongModel(UUID.randomUUID().toString(), 25, "Attention", "C.Puth", "B113 +03"))
        items.add(SongModel(UUID.randomUUID().toString(), 2, "Между нами любовь", "Серебро", "C032 SW1 SW2"))
        items.add(SongModel(UUID.randomUUID().toString(), 21, "К черту любовь", "Лобода", "B032 A1-6 +04"))
        items.add(SongModel(UUID.randomUUID().toString(), 9, "Мало половин", "Бузова", "A038 SW1 A1-5 +04"))
        items.add(SongModel(UUID.randomUUID().toString(), 16, "Попрошу тебя", "Вирус", "A038 SW1"))
        items.add(SongModel(UUID.randomUUID().toString(), 17, "Солнышко в руках", "Демo", "A038 SW1"))
        items.add(SongModel(UUID.randomUUID().toString(), 19, "Районы", "Звери", "D000 SW1 SW2 +06"))
        items.add(SongModel(UUID.randomUUID().toString(), 3, "Кружит", "Монатик", "A038 SW1 A1-7"))
        items.add(SongModel(UUID.randomUUID().toString(), 7, "Хали Гали", "Леприконсы", "C032 SW1 SW2 A1-7"))
        items.add(SongModel(UUID.randomUUID().toString(), 0, "Улети", "T-Fest", "COMBI A000"))
        return items
    }

    private fun configureToolbar() {
        toolbar.title = "Хоп хей"
        toolbar.setNavigationOnClickListener {
            removeAllSongs()
            addSongs(setCurrentDemoList())
        }
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.main)
        toolbar.menu.findItem(R.id.main_menu_add).setOnMenuItemClickListener {
            router.pushController(RouterTransaction.with(AddSongController()))
            true
        }
        toolbar.menu.findItem(R.id.main_menu_remove_all).setOnMenuItemClickListener {
            removeAllSongs()
            true
        }
    }

    private fun configureRecycler() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    private fun addSong(number: Int, name: String, artist: String) {
        val song = SongModel(UUID.randomUUID().toString(), number, name, artist)
        songsRepository.addSong(song)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun addSongs(items: List<SongModel>) {
        songsRepository.addSongs(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun removeAllSongs() {
        songsRepository.deleteAllSongs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_main, container, false)
}