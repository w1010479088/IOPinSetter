package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val IDS = arrayOf(
        R.id.action1, R.id.action2, R.id.action3, R.id.action4, R.id.action5,
        R.id.action6, R.id.action7, R.id.action8, R.id.action9, R.id.action10
    )
    private lateinit var mHelper: ViewHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHelper = ViewHelper(this.window.decorView)
        initView()
    }

    private fun initView() {
        IDS.forEach { _id ->
            mHelper.setClick(_id) {
                mHelper.setSel(_id, !mHelper.isSel(_id))
            }
        }
    }
}