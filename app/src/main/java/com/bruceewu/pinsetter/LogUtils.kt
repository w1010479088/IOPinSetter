package com.bruceewu.pinsetter

import android.util.Log

class LogUtils {
    companion object {
        fun log(content: String?) {
            if (BuildConfig.DEBUG) {
                Log.d("bruceewu", content ?: "")
            }
        }
    }
}