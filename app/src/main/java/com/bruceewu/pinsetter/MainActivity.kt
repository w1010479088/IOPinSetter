package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class MainActivity : AppCompatActivity(), ThreadPool.IUpdater {
    private lateinit var mSDKHelper: PinSDKHelper

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
            LogActivity.open(this)
        }
        IDS.forEachIndexed { index, _id ->
            mHelper.setClick(_id) {
                mSDKHelper.set(index, !mSDKHelper.get(index))
            }
        }
        ThreadPool.registerObserver(this)
    }

    override fun update() {
        try {
            IDS.forEachIndexed { index, id ->
                val high = mSDKHelper.get(index)
                mHelper.setSel(id, high)
            }
        } catch (ex: Exception) {
            LogUtils.log(ex.message)
        }
    }
}