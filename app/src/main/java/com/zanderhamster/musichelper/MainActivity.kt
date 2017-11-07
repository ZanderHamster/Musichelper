package com.zanderhamster.musichelper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.zanderhamster.musichelper.main.MainController


class MainActivity : AppCompatActivity() {

    private var router: Router? = null
    private lateinit var changeHandlerFrameLayout: ChangeHandlerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        changeHandlerFrameLayout = findViewById(R.id.changeHandlerFrameLayout)

        router = Conductor.attachRouter(this, changeHandlerFrameLayout, savedInstanceState)
        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(MainController()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

}
