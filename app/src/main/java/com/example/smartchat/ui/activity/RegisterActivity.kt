package com.example.smartchat.ui.activity

import com.example.smartchat.R
import com.example.smartchat.contract.RegisterContract
import com.example.smartchat.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast

class RegisterActivity : BaseActivity(),RegisterContract.View {

    val presenter by lazy { RegisterPresenter(this) }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }


    override fun onStartRegister() {
        showProgress(getString(R.string.registering))
    }

    override fun RegisterError() {
       dismissProgress()
       toast(R.string.register_fail)
    }

    override fun RegisterSuccess() {
        dismissProgress()
        toast(getString(R.string.register_success))
        finish()
    }

    override fun onUserNameError() {
        register_username.error = getString(R.string.username_error)
    }
    override fun onPassWordError() {
        register_password.error = getString(R.string.password_error)
    }
    override fun onConfirmPassError() {
        register_confirm_password.error = getString(R.string.password_error)
    }

    override fun initListener() {
        register_submit.setOnClickListener {
            register()//开始注册
        }
        register_confirm_password.setOnEditorActionListener { v, actionId, event ->
            register()
            true
        }
    }

    private fun register() {
        hideKeyBord()
        val username = register_username.text.toString().trim()
        val password = register_password.text.toString().trim()
        val confirmpass = register_confirm_password.text.toString().trim()
         presenter.register(username,password,confirmpass)
    }


}
