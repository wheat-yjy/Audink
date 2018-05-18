package com.kotlinproject.wooooo.audink.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.kotlinproject.wooooo.audink.R
import com.kotlinproject.wooooo.audink.activity.MainActivity
import com.kotlinproject.wooooo.audink.utils.AppCache
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_register.setOnClickListener {
            // 验证用户名
            val newName = edit_register_username.text.toString().trim()

            if (newName.length < 3) {
                Toast.makeText(this.context, "用户名太短", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 验证密码
            val pw1 = edit_register_password.text.toString()
            val pw2 = edit_register_password_config.text.toString()

            if (pw1.length < 6) {
                Toast.makeText(this.context, "密码最短六位", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pw1 != pw2) {
                Toast.makeText(this.context, "两次密码不一致", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 注册
            AppCache.asyncRegister(this.activity, edit_register_username.text.toString(), edit_register_password.text.toString()) { message, isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this.activity, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                } else {
                    Toast.makeText(this.context,message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
