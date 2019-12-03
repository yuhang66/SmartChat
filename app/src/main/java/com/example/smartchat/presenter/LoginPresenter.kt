package com.example.smartchat.presenter

import com.example.smartchat.contract.LoginContract
import com.example.smartchat.utils.isValidPassWord
import com.example.smartchat.utils.isValidUserName
import com.hyphenate.chat.a.d
import com.hyphenate.chat.EMClient
import com.hyphenate.EMCallBack
import android.R.attr.password



class LoginPresenter(val view:LoginContract.View):LoginContract.Presenter {
    override fun login(username: String, password: String) {
       if(username.isValidUserName()){//用户名合法
           if(password.isValidPassWord()){//密码合法
               view.onStartLogin()
               loginEaseMob(username,password)
           }else view.onPassWordError()
       }else{
           view.onUserNameError()
       }
    }

    private fun loginEaseMob(username: String, password: String) {
        EMClient.getInstance().login(username, password, object : EMCallBack {
            //回调
            override fun onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups()
                EMClient.getInstance().chatManager().loadAllConversations()
                uiThread { view.onLoginSuccess() }

            }

            override fun onProgress(progress: Int, status: String) {

            }

            override fun onError(code: Int, message: String) {
                uiThread { view.onLoginError() }
            }
        })

    }
}