package com.bruceewu.pinsetter

import android.text.TextUtils
import android.util.Log
import java.lang.StringBuilder

class LogUtils {
    companion object {
        private var builder = StringBuilder()
        private var interrupt: Boolean = true
        private var preLog: String? = null
        private var listener: Runnable? = null

        fun log(content: String?) {
            if (App.needLog() && !TextUtils.equals(content, preLog)) {
                if (!interrupt) {
                    builder.append(content ?: "------>")
                    builder.append("\n")
                }
                Log.d("BruceeWuTag:${Thread.currentThread().name}--->", content ?: "")
                preLog = content
                listener?.run()
            }
        }

        fun setListener(listener: Runnable) {
            this.listener = listener
        }

        fun read(): String = builder.toString()

        fun clear() {
            builder.clear()
        }

        fun clearListener() {
            this.listener = null
        }
    }
}