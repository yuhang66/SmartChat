package com.example.smartchat.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.smartchat.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.receive_message.view.*
import kotlinx.android.synthetic.main.send_message.view.*
import java.util.*

class ReceiveMessageItemView(context: Context?, attrs: AttributeSet?=null) :
    RelativeLayout(context, attrs) {
    fun bindView(emMessage: EMMessage, showTimestamp: Boolean) {
        updateMessage(emMessage)
        updateTimeStamp(emMessage, showTimestamp)
    }

    init {
        View.inflate(context, R.layout.receive_message, this)
    }

    private fun updateMessage(emMessage: EMMessage) {
        if (emMessage.type == EMMessage.Type.TXT) {
            receive_mess.text = (emMessage.body as EMTextMessageBody).message
        } else {
            receive_mess.text = context.getString(R.string.no_text_message)
        }
    }

    private fun updateTimeStamp(
        emMessage: EMMessage,
        showTimestamp: Boolean
    ) {
        if (showTimestamp) {
            receive_time.visibility = View.VISIBLE
            receive_time.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        } else {
            receive_time.visibility = View.GONE
        }
    }
}