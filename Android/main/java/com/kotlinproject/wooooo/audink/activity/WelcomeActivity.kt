package com.kotlinproject.wooooo.audink.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.view.View
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.utils.AppCache
import com.kotlinproject.wooooo.audink.utils.DownloadUtil
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlin.concurrent.thread

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class WelcomeActivity : Activity() {
    private val REQUEST_EXTERNAL_STORAGE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        // 公共类配置
        AppCache.start(this)
        DownloadUtil.start(this.application)

        // 权限申请
        val permission = ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"
                ),
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            login()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        login()
    }

    private fun login() {

        // 登录
        AppCache.autoLogin(this) { _, _ ->
            thread {
                Thread.sleep(1500)
                runOnUiThread {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
