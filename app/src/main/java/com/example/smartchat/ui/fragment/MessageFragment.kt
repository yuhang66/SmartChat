package com.example.smartchat.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartchat.R
import com.example.smartchat.adapter.MessageAdapter
import com.example.smartchat.adapter.UserAdapter
import com.example.smartchat.ui.activity.ChatRoomActivity
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.titlebar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MessageFragment: BaseFragment() {

    val conversations = mutableListOf<EMConversation>()

    val messageListener = object : EMMessageListener{
        override fun onMessageRecalled(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageChanged(p0: EMMessage?, p1: Any?) {

        }

        override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
             loadConversations()

        }

        override fun onMessageDelivered(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageRead(p0: MutableList<EMMessage>?) {

        }

    }

    override fun initView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_message,null)
    }

    override fun initData() {
        title_text.text = getString(R.string.message)
         message_recyclerview.apply {
             setHasFixedSize(true)
             layoutManager = LinearLayoutManager(context)
             adapter = MessageAdapter(conversations)



         }
        (message_recyclerview.adapter  as MessageAdapter).setOnItemChildClickListener { adapter, view, position ->
            val username = conversations[position].conversationId()
            context?.startActivity<ChatRoomActivity>("username" to username)
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        //loadConversations()
    }

    private fun loadConversations() {//读取数据
        doAsync {
            conversations.clear()
            val allConversations = EMClient.getInstance().chatManager().allConversations
            conversations.addAll(allConversations.values)
            uiThread { message_recyclerview.adapter!!.notifyDataSetChanged() }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

    override fun onResume() {
        super.onResume()
        loadConversations()
    }


}