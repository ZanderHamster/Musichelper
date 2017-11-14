package com.zanderhamster.musichelper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.zanderhamster.musichelper.di.ControllerComponent

import io.reactivex.disposables.CompositeDisposable

abstract class BaseController : ButterKnifeController() {
    protected val disposables = CompositeDisposable()
    private lateinit var unbinder: Unbinder
    lateinit var controllerComponent: ControllerComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        controllerComponent = (activity as MainActivity).activityComponent
                .plusController(ControllerComponent.ControllerModule())
        val view = inflateView(inflater, container)
        unbinder = ButterKnife.bind(this, view)
        return super.onCreateView(inflater, container)
    }

    override fun onDestroyView(view: View) {
        hideKeyboard()
        disposables.clear()
        unbinder.unbind()
        super.onDestroyView(view)
    }

    fun hideKeyboard() {
        val activity = activity ?: return
        val view = view ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
