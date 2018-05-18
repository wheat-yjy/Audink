package com.kotlinproject.wooooo.audink.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.MainActivity
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_login.setOnClickListener {
            AppCache.asyncLogin(this.activity, edit_login_username.text.toString(), edit_login_password.text.toString()){ message, isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this.activity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                } else {
                    Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
