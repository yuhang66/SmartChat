package com.example.smartchat.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.smartchat.R
import com.example.smartchat.data.UserBean
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(data:List<UserBean>):BaseQuickAdapter<UserBean,BaseViewHolder>(R.layout.user_item,data) {

    override fun convert(helper: BaseViewHolder, item: UserBean?) {
        item?:return
        if(item.showFirstLetter){
            helper.setVisible(R.id.user_item_num,true)
            helper.setText(R.id.user_item_num,item.firstLetter.toString())
        }else helper.setVisible(R.id.user_item_num,false)
            helper .setText(R.id.user_item_title,item.userName)

        helper.addOnLongClickListener(R.id.user_tou)
        helper.addOnClickListener(R.id.user_tou)
    }






}