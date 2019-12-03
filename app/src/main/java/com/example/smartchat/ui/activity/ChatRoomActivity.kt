package com.example.smartchat.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartchat.R
import com.example.smartchat.adapter.ChatAdapter
import com.example.smartchat.contract.ChatContract
import com.example.smartchat.presenter.ChatPresenter
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import kotlinx.android.synthetic.main.chat_room.*
import kotlinx.android.synthetic.main.titlebar.*
import org.jetbrains.anko.toast

class ChatRoomActivity : BaseActivity(),ChatContract.View {



    override fun getLayout(): Int {
        return R.layout.chat_room
    }

    val presenter = ChatPresenter(this)
    lateinit var username:String

    val messageListener  = object :EMMessageListener{
        override fun onMessageRecalled(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageChanged(p0: EMMessage?, p1: Any?) {

        }

        override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageReceived(p0: MutableList<EMMessage>?) {//接收到消息之后
            presenter.addMessage(username,p0)
            runOnUiThread{
                chat_recycler.adapter!!.notifyDataSetChanged()
                scrollToBottom()
            }


        }

        override fun onMessageDelivered(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageRead(p0: MutableList<EMMessage>?) {

        }

    }


    override fun initData() {
        title_back.visibility = View.VISIBLE
        title_back.setOnClickListener { finish() }
        //获取聊天的用户名
         username = intent.getStringExtra("username")
        val titleString = String.format(getString(R.string.chattitle),username)
        title_text.text =titleString

        room_edit.addTextChangedListener(object :TextWatcher{//文本框状态发生改变
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                room_send.isEnabled = !s.isNullOrEmpty()
            }

        })

        initRecyclerView()
        EMClient.getInstance().chatManager().addMessageListener(messageListener)

        presenter.loadMessage(username)
    }

    private fun initRecyclerView() {


        chat_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ChatAdapter(context,presenter.messages)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    //当recyclerview为空闲状态
                    //检查是否滑动到顶部要加载更多数据
                    if(newState==RecyclerView.SCROLL_STATE_IDLE){//空闲状态
                        //如果第一个可见条目位置是0 为滑到了顶部
                        val linearLayoutManager = layoutManager as LinearLayoutManager
                        if(linearLayoutManager.findFirstVisibleItemPosition()==0){
                            //加载更多数据
                            presenter.loadMessages(username)

                        }
                    }
                }
            })

        }
    }

    override fun onStartSendMessage() {
        chat_recycler.adapter!!.notifyDataSetChanged()
    }

    override fun onSendMessageSuccess() {
        chat_recycler.adapter!!.notifyDataSetChanged()
        toast(getString(R.string.send_success))
        room_edit.text.clear()//清空编辑框

        scrollToBottom()//消息发送完成 滚动到最后一条消息的位置
    }

    private fun scrollToBottom() {
        chat_recycler.scrollToPosition(presenter.messages.size-1)
    }

    override fun onSendMessageFailed() {
      toast(getString(R.string.send_err))
        chat_recycler.adapter!!.notifyDataSetChanged()

    }

    override fun initListener() {
        room_send.setOnClickListener {
            sendMessage()
        }
        room_edit.setOnEditorActionListener { v, actionId, event ->
            sendMessage()
            true
        }

    }

    private fun sendMessage() {
        hideKeyBord()
        presenter.sendMessage(username,room_edit.text.trim().toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }

    override fun onMessageLoaded() {
        chat_recycler.adapter!!.notifyDataSetChanged()
        scrollToBottom()
    }

    override fun onMoreMessageLoaded(size: Int) {
        chat_recycler.adapter!!.notifyDataSetChanged()
        chat_recycler.scrollToPosition(size)
    }
}
