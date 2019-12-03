package com.example.smartchat.contract

interface SplishContract {
    interface Presenter:BasePresenter{
        fun checkLoginStatus()//检查登陆状态
    }

    interface View{
        fun onNotLogined()//未登录ui业务逻辑
        fun onLogined()//已经登陆ui业务逻辑
    }
}