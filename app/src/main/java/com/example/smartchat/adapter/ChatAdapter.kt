package com.example.smartchat.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartchat.widget.ReceiveMessageItemView
import com.example.smartchat.widget.SendMessageItemView

import com.hyphenate.chat.EMMessage
import com.hyphenate.util.DateUtils

class ChatAdapter(val context: Context,val message:List<EMMessage>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        val ITEM_TYPE_SEND_MESSAGE= 0
        val ITEM_TYPE_RECEIVE_MESSAGE= 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType== ITEM_TYPE_SEND_MESSAGE){
            return SendMessageViewHolder(SendMessageItemView(context))
        }else return ReceiveMessageViewHolder(ReceiveMessageItemView(context))

    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val showTimestamp =  isShowTimeStamp(position)

         if(getItemViewType(position)== ITEM_TYPE_SEND_MESSAGE){//发送消息
             val sendMessageItemView = holder.itemView as SendMessageItemView
             sendMessageItemView.bindView(message[position],showTimestamp)
         }else{
             val receiveMessageItemView = holder.itemView as ReceiveMessageItemView
             receiveMessageItemView.bindView(message[position],showTimestamp)
         }
    }

    private fun isShowTimeStamp(position: Int):Boolean {
        //如果是第一条消息或者和前一条间隔时间长才显示时间
        var showTimeStamp = true
        if(position>0){
            showTimeStamp = !DateUtils.isCloseEnough(message[position].msgTime,message[position-1].msgTime)
        }
        return showTimeStamp

    }

    override fun getItemViewType(position: Int): Int {
        if(message[position].direct()==EMMessage.Direct.SEND){
            return ITEM_TYPE_SEND_MESSAGE
        }else{
            return ITEM_TYPE_RECEIVE_MESSAGE
        }
    }

    class SendMessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


    }

    class ReceiveMessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
}