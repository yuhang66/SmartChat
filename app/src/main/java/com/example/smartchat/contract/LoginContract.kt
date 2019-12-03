package com.example.smartchat.contract

interface LoginContract {
    interface Presenter:BasePresenter{

        fun login(username:String,password:String)
    }
    interface View{
        fun onUserNameError()
        fun onPassWordError()
        fun onStartLogin()
        fun onLoginSuccess()
        fun onLoginError()
    }
}