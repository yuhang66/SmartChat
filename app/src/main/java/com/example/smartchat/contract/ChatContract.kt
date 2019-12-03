package com.example.smartchat.contract

import com.hyphenate.chat.EMMessage

interface ChatContract {
    interface Presenter:BasePresenter{
        fun sendMessage(username:String,message:String)
        fun addMessage(username: String, p0: MutableList<EMMessage>?)
        fun loadMessage(username: String)
        fun loadMessages(username: String)
    }

    interface View{
        fun onStartSendMessage()
        fun onSendMessageSuccess()
        fun onSendMessageFailed()
        fun onMessageLoaded()
        fun onMoreMessageLoaded(size: Int)
    }
}