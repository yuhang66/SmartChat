package com.example.smartchat.adapter

import androidx.core.content.res.TypedArrayUtils.getString
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.smartchat.R
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import java.util.*

class MessageAdapter(data:List<EMConversation>):BaseQuickAdapter<EMConversation,BaseViewHolder>(R.layout.message_item,data) {
    override fun convert(helper: BaseViewHolder, item: EMConversation?) {
        item?:return
       helper.setText(R.id.message_item_name,item.conversationId())
        if(item.lastMessage.type==EMMessage.Type.TXT){
            val body = item.lastMessage.body as EMTextMessageBody
            var mess:String = body.message
            helper.setText(R.id.message_item_message,mess)
        }else{

            helper.setText(R.id.message_item_message,"非文本消息")
        }
        val timestampString = DateUtils.getTimestampString(Date(item.lastMessage.msgTime))
        helper.setText(R.id.message_item_time,timestampString)

        if(item.unreadMsgCount>0){
            helper.setVisible(R.id.message_item_hint,true)
            helper.setText(R.id.message_item_hint,item.unreadMsgCount.toString())
        }else{
            helper.setVisible(R.id.message_item_hint,false)
        }

        helper.addOnClickListener(R.id.message_item_tou)
    }


}