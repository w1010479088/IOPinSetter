package com.bruceewu.pinsetter

import android.util.Log
import java.lang.StringBuilder

class LogUtils {
    companion object {
        private var builder = StringBuilder()
        private var interrupt: Boolean = true

        fun log(content: String?) {
            if (App.needLog()) {
                if (!interrupt) {
                    builder.append(content ?: "------>")
                    builder.append("\n")
                }
                Log.d("BruceeWuTag:${Thread.currentThread().name}--->", content ?: "")
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