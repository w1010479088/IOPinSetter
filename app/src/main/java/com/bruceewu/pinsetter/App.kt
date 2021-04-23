package com.bruceewu.pinsetter

import android.app.Application

class App : Application() {

    companion object {
        private var app: Application? = null

        fun getInstance(): Application? {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}