package com.example.smartchat.presenter

import com.example.smartchat.contract.ChatContract
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.EMCallBack
import org.jetbrains.anko.doAsync


class ChatPresenter(val view:ChatContract.View):ChatContract.Presenter {

  companion object{
      val PAGE_SIZE = 10
  }

    val messages = mutableListOf<EMMessage>()

    override fun sendMessage(username: String, message: String) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        val message = EMMessage.createTxtSendMessage(message, username)
//如果是群聊，设置chattype，默认是单聊
//        if (chatType === CHATTYPE_GROUP)
//            message.chatType = ChatType.GroupChat
//发送消息

        message.setMessageStatusCallback(object : EMCallBack {
            override fun onSuccess() {
                uiThread { view.onSendMessageSuccess() }

            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
              uiThread { view.onSendMessageFailed()  }

            }

        })
        messages.add(message)
        view.onStartSendMessage()
        EMClient.getInstance().chatManager().sendMessage(message)
    }
    override fun addMessage(username: String, p0: MutableList<EMMessage>?) {
        p0?.let { messages.addAll(it) }
        //更新消息为已读
        val conversation = EMClient.getInstance().chatManager().getConversation(username)
        conversation.markAllMessagesAsRead()
    }


    override fun loadMessage(username: String) { //加载历史聊天记录
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)
            conversation.markAllMessagesAsRead()
            messages.addAll(conversation.allMessages)
            uiThread {
                view.onMessageLoaded()
           }
        }


    }
    override fun loadMessages(username: String) { //加载多条历史聊天记录
        doAsync {
            val conversation = EMClient.getInstance().chatManager().getConversation(username)

            val startMsgId = messages[0].msgId
            val loadMoreMsgFromDB = conversation.loadMoreMsgFromDB(startMsgId, PAGE_SIZE)
            messages.addAll(0,loadMoreMsgFromDB)
            uiThread {  view.onMoreMessageLoaded(loadMoreMsgFromDB.size)}
        }

    }

}