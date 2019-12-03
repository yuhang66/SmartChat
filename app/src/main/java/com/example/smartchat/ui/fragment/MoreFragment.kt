package com.example.smartchat.ui.fragment

import android.view.LayoutInflater
import android.view.View
import com.example.smartchat.R
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.titlebar.*
import com.hyphenate.EMCallBack

import com.example.smartchat.ui.activity.LoginActivity
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MoreFragment: BaseFragment() {

    override fun initView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_more,null)
    }

    override fun initData() {
        title_text.text  = getString(R.string.dongtai)
        more_unlog.text = getString(R.string.unlog)

        val logout = String.format(getString(R.string.unlog),EMClient.getInstance().currentUser)
        more_unlog.text = logout
    }

    override fun initListener() {
        more_unlog.setOnClickListener {
            logout()//退出登录
        }
    }

    private fun logout() {
        EMClient.getInstance().logout(true, object : EMCallBack {

            override fun onSuccess() {
              context?.runOnUiThread {
                  toast(R.string.outsuccess)
                  startActivity<LoginActivity>()
                  activity?.finish()
              }

            }

            override fun onProgress(progress: Int, status: String) {


            }

            override fun onError(code: Int, message: String) {
                context?.runOnUiThread {
                    toast(R.string.outfail)
                }


            }
        })
    }

}