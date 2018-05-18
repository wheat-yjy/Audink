package com.kotlinproject.wooooo.audink.activity

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.fragment.LoginFragment
import com.kotlinproject.wooooo.audink.fragment.RegisterFragment
import com.kotlinproject.wooooo.audink.service.BackgroundAudioService
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.activity_login_register.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class LoginRegisterActivity : BaseActivity() {

    private lateinit var audioBinder: BackgroundAudioService.BackgroundAudioBinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        initView()
    }

    override fun onBgmServiceBind(service: IBinder?) {
        super.onBgmServiceBind(service)
        audioBinder = service as BackgroundAudioService.BackgroundAudioBinder
        audioBinder.isPlaying = false
    }

    private fun initView() {
        viewpager_login_register.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            private val titleList = listOf("账号登录", "注册新用户")
            private val fragmentList = listOf(LoginFragment(), RegisterFragment())
            override fun getPageTitle(position: Int): CharSequence  = titleList[position]
            override fun getItem(position: Int): Fragment  = fragmentList[position]
            override fun getCount(): Int = fragmentList.size
        }
        tab_login_register.setupWithViewPager(viewpager_login_register)
    }
}
