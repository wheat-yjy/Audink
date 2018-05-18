package com.kotlinproject.wooooo.audink.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kotlinproject.wooooo.audink.service.BackgroundAudioService
import kotlinx.android.synthetic.main.layout_toolbar.*

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mService : MyServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        volumeControlStream = AudioManager.STREAM_MUSIC
        bindService()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initView()
    }

    /** 绑定背景音频播放服务调用的方法 */
    protected open fun onBgmServiceBind(service: IBinder?) {}

    /** 绑定下载服务调用的方法 */
    protected open fun onDownloadServiceBind(service: IBinder?) {}

    private fun initView() {
        if (toolbar == null) {
            throw IllegalStateException("布局中找不到toolbar！")
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun bindService() {
        val intent = Intent(this, BackgroundAudioService::class.java)
        mService = MyServiceConnection()
        bindService(intent, mService, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mService)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class MyServiceConnection: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            onBgmServiceBind(service)
        }
    }
}