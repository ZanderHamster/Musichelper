package com.zanderhamster.musichelper.db

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongsRepository @Inject constructor(appDatabase: AppDatabase) {
    private val songDao = appDatabase.songDao()

    private val songsSubject = BehaviorSubject.create<List<SongModel>>()

    init {
        updateSongsSubject()
    }

    fun addSong(model: SongModel): Completable {
        if (model == SongModel.EMPTY) return Completable.complete()

        return Completable
                .fromAction({ songDao.insert(model.transform()) })
                .doOnSubscribe { Timber.e("Start adding song to DB") }
                .doOnComplete(this::updateSongsSubject)
                .doOnError { err -> Timber.e(err) }

    }

    fun addSongs(items: List<SongModel>): Completable {
        if (items.isEmpty()) return Completable.complete()

        val list = items.map { it.transform() }

        return Completable
                .fromAction({ songDao.insert(list) })
                .doOnSubscribe { Timber.e("Start adding songs to DB") }
                .doOnComplete(this::updateSongsSubject)
                .doOnError { err -> Timber.e(err) }

    }

    fun deleteAllSongs(): Completable {
        return Completable
                .fromAction({ songDao.deleteAllSongs() })
                .doOnSubscribe { Timber.e("Start deleteAllSongs to DB") }
                .doOnComplete(this::updateSongsSubject)
                .doOnError { err -> Timber.e(err) }

    }

    private fun updateSongsSubject() {
        Single.fromCallable(songDao::getAllSongs)
                .map({ items -> items.map(SongEntity::transform) })
                .onErrorReturnItem(emptyList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songsSubject::onNext)
    }

    fun getSongsObservable(): Observable<List<SongModel>> = songsSubject.hide()

}

private fun SongModel.transform(): SongEntity {
    return SongEntity(
            id = songId,
            name = name,
            number = number,
            artist = artist
    )
}

private fun SongEntity.transform(): SongModel {
    return SongModel(
            songId = id,
            name = name,
            number = number,
            artist = artist
    )
}