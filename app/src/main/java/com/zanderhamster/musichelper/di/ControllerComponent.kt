package com.zanderhamster.musichelper.di

import com.zanderhamster.musichelper.addSong.AddSongController
import com.zanderhamster.musichelper.main.MainController
import dagger.Module
import dagger.Subcomponent


@PerController
@Subcomponent(modules = arrayOf(ControllerComponent.ControllerModule::class))
interface ControllerComponent {
    @Module
    class ControllerModule

    fun inject(controller: MainController)
    fun inject(controller: AddSongController)
}