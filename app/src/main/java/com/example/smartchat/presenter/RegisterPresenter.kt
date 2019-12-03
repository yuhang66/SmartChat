package com.example.smartchat.presenter

import com.example.smartchat.contract.RegisterContract
import com.example.smartchat.utils.isValidPassWord
import com.example.smartchat.utils.isValidUserName
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import org.jetbrains.anko.doAsync

class RegisterPresenter(val view:RegisterContract.View):RegisterContract.Presenter {
    override fun register(username: String, password: String, confirmPassword: String) {
        if(username.isValidUserName()){
            if(password.isValidPassWord()){
                if(confirmPassword.isValidPassWord()){

                    view.onStartRegister()
                    //注册开始
                    registerEaseMob(username,password)


                }else view.onConfirmPassError()

            }else view.onPassWordError()
        }else view.onUserNameError()

    }

    private fun registerEaseMob(username: String, password: String) {
        doAsync {
            try {
                EMClient.getInstance().createAccount(username, password)  //同步方法
                uiThread { view.RegisterSuccess() }
            }catch (e: HyphenateException){
               //注册失败
                uiThread { view.RegisterError() }
            }
        }

    }
}