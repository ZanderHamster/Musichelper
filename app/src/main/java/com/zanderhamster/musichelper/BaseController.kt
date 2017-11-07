package com.zanderhamster.musichelper

import android.view.View

import io.reactivex.disposables.CompositeDisposable

abstract class BaseController : ButterKnifeController() {
    private val disposables = CompositeDisposable()

    override fun onDestroyView(view: View) {
        disposables.clear()
        super.onDestroyView(view)
    }
}
