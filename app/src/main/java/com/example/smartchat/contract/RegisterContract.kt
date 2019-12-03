package com.example.smartchat.contract

interface RegisterContract {
    interface Presenter:BasePresenter{
        fun register(username:String,password:String,confirmPassword:String)
    }
    interface View{
        fun onUserNameError()
        fun onPassWordError()
        fun onConfirmPassError()
        fun onStartRegister()
        fun RegisterError()
        fun RegisterSuccess()
    }
}