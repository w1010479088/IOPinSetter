package com.bruceewu.pinsetter

import android.app.smdt.SmdtManager
import android.content.Context
import com.bruceewu.pinsetter.LogUtils.Companion.log

interface IGPIO {

    fun read(num: Int): Boolean

    fun set(num: Int, high: Boolean): Boolean

    companion object {

        fun newInstance(context: Context): IGPIO {
            if (BuildConfig.DEBUG) {
                return DebugGPIO()
            } else {
                return ReleaseGPIO(context)
            }
        }
    }
}

class DebugGPIO : IGPIO {

    private val IOS = listOf(
        IOState(false),
        IOState(false),
        IOState(false),
        IOState(false),
        IOState(false),
        IOState(false),
        IOState(false)
    )

    override fun read(num: Int) = IOS[num].high

    override fun set(num: Int, high: Boolean): Boolean {
        IOS[num].high = high
        return true
    }
}

class ReleaseGPIO(context: Context) : IGPIO {
    private var manager: SmdtManager? = null

    init {
        manager = SmdtManager.create(context)
        if (manager == null) {
            log("PinSdk init fail")
        } else {
            log("PinSdk init success")
        }
    }

    override fun read(num: Int): Boolean {
        return manager?.smdtGetXrm117xGpioValue(num) == 1
    }

    override fun set(num: Int, high: Boolean): Boolean {
        return manager?.smdtSetXrm117xGpioValue(num, if (high) 1 else 0) == 0    // 0是成功,-1是失败
    }
}

data class IOState(var high: Boolean)