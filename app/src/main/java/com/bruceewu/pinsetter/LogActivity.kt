package com.bruceewu.pinsetter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

class LogActivity : Activity() {
    private lateinit var mHelper: ViewHelper

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, LogActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        mHelper = ViewHelper(this.window.decorView)
        initView()
    }

    private fun initView() {
        mHelper.setClick(R.id.back) {
            finish()
        }
        mHelper.setClick(R.id.clear) {
            LogUtils.clear()
            refreshLog()
        }
        mHelper.setClick(R.id.interrupt) {
            LogUtils.interrupt()
            refreshInterrupt()
        }
        refreshInterrupt()
        refreshLog()
    }

    private fun refreshInterrupt() {
        mHelper.setText(R.id.interrupt, if (LogUtils.isInterrupt()) "记录中断中..." else "正在记录中...")
    }

    private fun refreshLog() {
        mHelper.setText(R.id.log, LogUtils.read())
    }
}