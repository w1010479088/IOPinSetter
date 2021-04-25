package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class MainActivity : AppCompatActivity(), ThreadPool.IUpdater {
    private lateinit var mSDKHelper: PinSDKHelper
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

    }

    private fun loop() {
        if (mLooper) {
            ThreadPool.unRegisterObserver(this)
            mLooper = false
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