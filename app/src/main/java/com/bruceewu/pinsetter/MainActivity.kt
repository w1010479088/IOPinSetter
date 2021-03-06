package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class MainActivity : AppCompatActivity(), ThreadPool.IUpdater {
    private var mPreState: GPIOLoopColorState = GPIOLoopColorState.BLACK
    private var mLooper: Boolean = false

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
        PinSDKHolder.init()
        mHelper.setClick(R.id.action08) {
            loop()
        }
        IDS.forEachIndexed { index, _id ->
            mHelper.setClick(_id) {
                if (mLooper) { //循环中的按钮,不能点击
                    ToastUtils.show("循环中,不能点击...")
                } else {
                    val high = !PinSDKHolder.get(index)
                    val success = PinSDKHolder.set(index, high)
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
        mPreState = mPreState.next()
        showState(mPreState)
    }

    private fun showState(color: GPIOLoopColorState) {
        LogUtils.log("展示:${color.tag}")
        IDS.forEachIndexed { index, i ->
            PinSDKHolder.set(index, color.state.get(index))
            if (App.needColorRefresh()) {
                mHelper.setSel(i, PinSDKHolder.get(index))
            }
        }
    }

    private fun loop() {
        if (mLooper) {
            ThreadPool.unRegisterObserver(this)
            IDS.forEachIndexed { index, id ->
                PinSDKHolder.set(index, true)
            }
            mLooper = false
            mPreState = GPIOLoopColorState.BLACK
            refresh()
        } else {
            ThreadPool.registerObserver(this)
            IDS.forEach { id ->
                mHelper.setSel(id, true)
            }
            mLooper = true
        }
        mHelper.setSel(R.id.action08, mLooper)
    }

    private fun refresh() {
        try {
            IDS.forEachIndexed { index, id ->
                val high = PinSDKHolder.get(index)
                mHelper.setSel(id, high)
            }
        } catch (ex: Exception) {
            LogUtils.log(ex.message)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    override fun onDestroy() {
        PinSDKHolder.onDestroy()
        ThreadPool.unRegisterObserver(this)
        super.onDestroy()
    }
}

