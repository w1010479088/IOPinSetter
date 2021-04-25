package com.bruceewu.pinsetter

import android.app.Application

class App : Application() {

    companion object {
        private var app: Application? = null

        fun getInstance(): Application? {
            return app
        }

        fun debugIO(): Boolean = debug()

        fun needLog(): Boolean = debug()

        fun needIOObserver(): Boolean = debug()

        private fun debug(): Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}