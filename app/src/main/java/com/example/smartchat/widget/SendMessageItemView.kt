package com.example.smartchat.widget

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.smartchat.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody
import com.hyphenate.util.DateUtils
import kotlinx.android.synthetic.main.send_message.view.*
import java.util.*

class SendMessageItemView(context: Context?, attrs: AttributeSet?=null) :
    RelativeLayout(context, attrs) {


    init {
        View.inflate(context, R.layout.send_message,this)
    }
    fun bindView(emMessage: EMMessage, showTimestamp: Boolean) {
           updateTimeStamp(emMessage,showTimestamp)
          updateMessage(emMessage)
          updateProgress(emMessage)
    }

    private fun updateProgress(emMessage: EMMessage) {
        emMessage.status().let {
            when(it){

                EMMessage.Status.INPROGRESS->{
                    send_load.visibility = View.VISIBLE
                    send_load.setImageResource(R.drawable.load)
//                    val animationDrawable = send_load.drawable as AnimationDrawable
//                    animationDrawable.start()
                }
                EMMessage.Status.SUCCESS->{
                    send_load.visibility = View.GONE
                }
                EMMessage.Status.FAIL->{
                    send_load.visibility = View.VISIBLE
                    send_load.setImageResource(R.drawable.fail)
                }
            }
        }
    }

    private fun updateMessage(emMessage: EMMessage) {
        if(emMessage.type ==EMMessage.Type.TXT){
            send_mess.text = (emMessage.body as EMTextMessageBody).message
        }else{
            send_mess.text =context.getString(R.string.no_text_message)
        }
    }

    private fun updateTimeStamp(
        emMessage: EMMessage,
        showTimestamp: Boolean
    ) {
        if(showTimestamp){
            send_time.visibility = View.VISIBLE
            send_time.text = DateUtils.getTimestampString(Date(emMessage.msgTime))
        }else{
            send_time.visibility = View.GONE
        }


    }
}