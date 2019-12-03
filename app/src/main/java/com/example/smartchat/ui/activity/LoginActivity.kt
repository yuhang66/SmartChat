package com.example.smartchat.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.smartchat.R
import com.example.smartchat.contract.LoginContract
import com.example.smartchat.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity(),LoginContract.View {

    val presenter by lazy { LoginPresenter(this) }

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    //用户名格式错误
    override fun onUserNameError() {
        login_username.error = getString(R.string.username_error)
    }

    override fun onPassWordError() {
        login_password.error = getString(R.string.password_error)
    }

    override fun onStartLogin() {
        //弹出进度条
        showProgress(getString(R.string.logining))
    }

    override fun onLoginSuccess() {
        dismissProgress()
        startActivity<MainActivity>()
        finish()
    }

    override fun onLoginError() {
        dismissProgress()
        toast(R.string.login_error)
    }

    override fun initListener() {//登录点击事件
        login_submit.setOnClickListener { login() }
        login_password.setOnEditorActionListener { v, actionId, event ->
            login()
             true
        }
        login_new.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

    private fun login() {
        hideKeyBord()
        if(hasReadPermission()){
            var userText = login_username.text.toString().trim()
            var passText = login_password.text.toString().trim()
            presenter.login(userText,passText)
        }else{
            requestPermission()
        }


    }


    //申请权限
    private fun requestPermission() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this,permissions,0)
    }

    //判断是否有该权限
    private fun hasReadPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED){//用户同意权限
            login()
        }else{
            toast(getString(R.string.permission_denied))
        }
    }


}
