package com.example.smartchat.utils

import androidx.fragment.app.Fragment
import com.example.smartchat.R
import com.example.smartchat.ui.fragment.BaseFragment

import com.example.smartchat.ui.fragment.MessageFragment
import com.example.smartchat.ui.fragment.MoreFragment
import com.example.smartchat.ui.fragment.UserFragment

class FragmentUtil {

    val messageFragment by lazy { MessageFragment() }
    val userFragment by lazy { UserFragment() }
    val moreFragment by lazy { MoreFragment() }
    companion object{//伴生对象 就是java中的static
    val fragmentUtil by lazy { FragmentUtil() }//惰性加载
    }
    //根据tabid获取对应的fragment
    fun getFragment(tabid:Int): Fragment?{
        when(tabid){
            R.id.tab_message->return messageFragment
            R.id.tab_friends->return userFragment
            R.id.tab_more->return moreFragment
        }
        return null
    }
}