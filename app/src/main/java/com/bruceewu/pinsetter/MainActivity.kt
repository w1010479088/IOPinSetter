package com.bruceewu.pinsetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var mHelper: ViewHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mHelper = ViewHelper(this.window.decorView)
        initView()
    }

    private fun initView() {
        mHelper.setClick(R.id.action1) {
            ToastUtils.show("action1")
        }
    }
}