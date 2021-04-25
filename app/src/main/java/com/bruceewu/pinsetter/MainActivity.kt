package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class MainActivity : AppCompatActivity(), ThreadPool.IUpdater {
    private lateinit var mSDKHelper: PinSDKHelper
    private var mPreState: GPIOLoopColorState = GPIOLoopColorState.NONE
    private var mLooper: Boolean = false
    private var mCount = 0

    private val IDS = arrayOf(
        R.id.action00, R.id.action01, R.id.action02, R.id.action03,
        R.id.action04, R.id.action05, R.id.action06, R.id.action07
    )
    private lateinit var mHelper: ViewHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mHelper = ViewHelper(this.window.decorView)
        mSDKHelper = PinSDKHelper(this)
        mHelper.setClick(R.id.action08) {
            loop()
        }
        IDS.forEachIndexed { index, _id ->
            mHelper.setClick(_id) {
                if (mLooper) { //循环中的按钮,不能点击
                    ToastUtils.show("循环中,不能点击...")
                } else {
                    val high = !mSDKHelper.get(index)
                    val success = mSDKHelper.set(index, high)
                    if (success) {
                        mHelper.setSel(_id, high)
                    } else {
                        ToastUtils.show("操作失败!")
                    }
                }
            }
        }
        refresh()
    }

    override fun update() {
        if (mCount == 0 || mCount == 1) {
            if (mCount == 0) {  // 亮屏 1s
                mPreState = mPreState.next()
            }
            showState(mPreState)
        } else {
            showState(GPIOLoopColorState.NONE)
        }

        // mCount检查/重置
        if (mCount >= 2) {
            mCount = 0
        } else {
            mCount++
        }
    }

    private fun showState(color: GPIOLoopColorState) {
        LogUtils.log("展示:${color.tag}")
        IDS.forEachIndexed { index, i ->
            mSDKHelper.set(index, color.state.get(index))
        }
    }

    private fun loop() {
        mCount = 0
        if (mLooper) {
            ThreadPool.unRegisterObserver(this)
            reset()
            mLooper = false
            mPreState = GPIOLoopColorState.NONE
        } else {
            reset() // 先全部重置UI以及状态值,然后开启循环
            ThreadPool.registerObserver(this)
            mLooper = true
        }
        mHelper.setSel(R.id.action08, mLooper)
    }

    private fun reset() {
        try {
            IDS.forEachIndexed { index, id ->
                mSDKHelper.set(index, false)
                mHelper.setSel(id, false)
            }
        } catch (ex: Exception) {
            LogUtils.log(ex.message)
        }
    }

    private fun refresh() {
        try {
            IDS.forEachIndexed { index, id ->
                val high = mSDKHelper.get(index)
                mHelper.setSel(id, high)
            }
        } catch (ex: Exception) {
            LogUtils.log(ex.message)
        }
    }

    override fun onDestroy() {
        ThreadPool.unRegisterObserver(this)
        mSDKHelper.onDestroy()
        super.onDestroy()
    }
}

