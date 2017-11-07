package com.zanderhamster.musichelper


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bluelinelabs.conductor.Controller

import butterknife.ButterKnife
import butterknife.Unbinder

/*
 * Контроллер, добавляющий функционал ButterKnife.
 *
 * Дочерние контроллеры могут пользоваться View-полями начиная с метода [.onViewBound]
 *
 * При закрытии контроллера ([.onDestroyView]) ссылки на view зануляются.
 */
abstract class ButterKnifeController : Controller {

    private var unbinder: Unbinder? = null

    constructor() {}

    constructor(args: Bundle) : super(args) {}

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        unbinder = ButterKnife.bind(this, view)
        onViewBound(view)
        return view
    }

    /* Вызывается после создания View, когда ButterKnife привязал поля  */
    protected open fun onViewBound(view: View) {}

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        unbinder!!.unbind()
        unbinder = null
    }
}