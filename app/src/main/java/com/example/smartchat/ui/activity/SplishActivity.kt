package com.example.smartchat.ui.activity

import android.os.Handler
import com.example.smartchat.R
import com.example.smartchat.contract.SplishContract
import com.example.smartchat.presenter.SplishPresenter
import org.jetbrains.anko.startActivity

class SplishActivity : BaseActivity(),SplishContract.View {

    private val handler by lazy { Handler() }
    val preseter = SplishPresenter(this)
    override fun getLayout(): Int {
        return R.layout.activity_splish
    }

    override fun initData() {
        preseter.checkLoginStatus()
    }

    override fun onLogined() {
          startActivity<MainActivity>()
          finish()
    }
     //未登录 延时2秒跳转到登陆页面
    override fun onNotLogined() {
        handler.postDelayed({
            startActivity<LoginActivity>()
            finish()
        },2000)
    }

}
