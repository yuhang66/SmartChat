package com.example.smartchat.contract

import android.os.Handler
import android.os.Looper

interface BasePresenter{

    //子线程主线程之间切换
    companion object{
        val handler by lazy { Handler(Looper.getMainLooper()) }
    }
    fun uiThread(f:() -> Unit){
        handler.post { f() }
    }
}