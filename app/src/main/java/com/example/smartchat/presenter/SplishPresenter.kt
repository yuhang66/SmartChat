package com.example.smartchat.presenter

import com.example.smartchat.contract.SplishContract
import com.hyphenate.chat.EMClient

class SplishPresenter(var view:SplishContract.View):SplishContract.Presenter {

    override fun checkLoginStatus() {
        if (isLogined()) view.onLogined() else view.onNotLogined()
    }

    private fun isLogined(): Boolean =
        EMClient.getInstance().isConnected && EMClient.getInstance().isLoggedInBefore


}