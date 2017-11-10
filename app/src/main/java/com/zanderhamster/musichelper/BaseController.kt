package com.zanderhamster.musichelper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zanderhamster.musichelper.di.ControllerComponent

import io.reactivex.disposables.CompositeDisposable

abstract class BaseController : ButterKnifeController() {
    protected val disposables = CompositeDisposable()
    lateinit var controllerComponent: ControllerComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        controllerComponent = (activity as MainActivity).activityComponent
                .plusController(ControllerComponent.ControllerModule())
        return super.onCreateView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        disposables.clear()
        super.onDestroyView(view)
    }
}
