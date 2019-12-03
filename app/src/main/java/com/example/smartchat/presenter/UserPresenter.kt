package com.example.smartchat.presenter

import com.example.smartchat.contract.UserContract
import com.example.smartchat.data.UserBean
import org.jetbrains.anko.doAsync
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException


class UserPresenter(val view:UserContract.View):UserContract.Presenter {

    val userbeanItem = mutableListOf<UserBean>()
    override fun loadContracts() {
        doAsync { //anko库开启线程
            //获取好友列表
            try {
                userbeanItem.clear()
                val usernames = EMClient.getInstance().contactManager().allContactsFromServer
                usernames.sortBy { it[0] }  //排序

                usernames.forEachIndexed { index, s ->

                    val showFirstLetters = index==0 || s[0] != usernames[index-1][0]
                    val userbean = UserBean(s,s[0].toUpperCase(),showFirstLetters)
                    userbeanItem.add(userbean)
                }


                uiThread { view.loadSuccess() }
            }catch (e: HyphenateException){
                uiThread { view.loadError() }
            }


        }
        //userbeanItem.clear()
    }
}