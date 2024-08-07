package com.bruceewu.pinsetter

import android.content.Context
import com.bruceewu.pinsetter.IGPIO.Companion.newInstance
import com.bruceewu.pinsetter.ThreadPool.IUpdater

object PinSDKHolder {
    private val sdk = PinSDKHelper(App.getInstance())

    fun init() {
        sdk.initSDK()
    }

    fun get(pos: Int): Boolean = sdk[pos]

    fun set(pos: Int, high: Boolean): Boolean = sdk.set(pos, high)

    fun onDestroy() {
        sdk.onDestroy()
    }
}

class PinSDKHelper(context: Context?) : IUpdater {
    private val mIOControl: IGPIO = newInstance(context!!)
    private val mIOLogs = StringBuilder()
    private val IO_SIZE = 8

    init {
        reset()
        if (App.needIOObserver()) {
            ThreadPool.registerObserver(this) //Debug 检测IO变化
        }
    }

    fun initSDK() {
        LogUtils.log("SDK init")
    }

    operator fun get(num: Int): Boolean {
        return try {
            mIOControl.read(num)
        } catch (ex: Exception) {
            logError("get", ex)
            false
        }
    }

    operator fun set(num: Int, high: Boolean): Boolean {
        try {
            return mIOControl.set(num, high)
        } catch (ex: Exception) {
            logError("set", ex)
            return false
        }
    }

    private var mCount = 0

    override fun update() {
        if (mCount % 5 == 0) {  // 5s中一次
            mIOLogs.clear()
            mIOLogs.append("--->")
            for (i in 0 until IO_SIZE) {
                mIOLogs.append(if (get(i)) 1 else 0)
                mIOLogs.append("-")
            }
            mIOLogs.append("---<")
            LogUtils.log(mIOLogs.toString())
            mCount = 0
        }
        mCount++
    }

    fun onDestroy() {
        ThreadPool.unRegisterObserver(this)
    }

    private fun logError(tag: String, ex: Exception) {
        LogUtils.log("$tag Error = " + ex.message)
    }

    //需求:100,001,11
    private fun reset() {
        val ioReset = listOf(
            IOState(true), IOState(false), IOState(false),
            IOState(false), IOState(false), IOState(true),
            IOState(true), IOState(true)
        )
        for (i in 0 until IO_SIZE) {
            set(i, ioReset[i].high)
        }
    }
}