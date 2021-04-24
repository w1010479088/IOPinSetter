package com.bruceewu.pinsetter

import android.util.Log
import java.lang.StringBuilder

class LogUtils {
    companion object {
        private var builder = StringBuilder()
        private var interrupt: Boolean = false

        fun log(content: String?) {
            if (BuildConfig.DEBUG) {
                if (!interrupt) {
                    builder.append(content ?: "------>")
                    builder.append("\n")
                }
                Log.d("bruceewu", content ?: "")
            }
        }

        fun read(): String = builder.toString()

        fun clear() {
            builder.clear()
        }

        fun interrupt() {
            interrupt = !interrupt
        }

        fun isInterrupt() = interrupt
    }
}