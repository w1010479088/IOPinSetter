package com.bruceewu.pinsetter

import android.content.Context
import com.bruceewu.pinsetter.IGPIO.Companion.newInstance
import com.bruceewu.pinsetter.LogUtils.Companion.log
import com.bruceewu.pinsetter.ThreadPool.IUpdater

class PinSDKHelper(context: Context?) : IUpdater {
    private val mIOControl: IGPIO = newInstance(context!!)
    private val mIOLogs = StringBuilder()
    private val IO_SIZE = 8

    init {
        if (App.needIOObserver()) {
            ThreadPool.registerObserver(this) //Debug 检测IO变化
        }
    }

    operator fun get(num: Int): Boolean {
        return try {
            mIOControl.read(num)
        } catch (ex: Exception) {
            logError(ex)
            false
        }
    }

    operator fun set(num: Int, high: Boolean) {
        try {
            mIOControl.set(num, high)
        } catch (ex: Exception) {
            logError(ex)
        }
    }

    override fun update() {
        mIOLogs.clear()
        mIOLogs.append("--->")
        for (i in 0 until (IO_SIZE - 1)) {
            mIOLogs.append(if (get(i)) 1 else 0)
            mIOLogs.append("-")
        }
        mIOLogs.append("---<")
        log(mIOLogs.toString())
    }

    fun onDestroy() {
        ThreadPool.unRegisterObserver(this)
    }

    private fun logError(ex: Exception) {
        log("set Error = " + ex.message)
    }
}