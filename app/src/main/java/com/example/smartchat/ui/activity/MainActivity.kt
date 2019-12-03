package com.example.smartchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartchat.R
import kotlinx.android.synthetic.main.activity_main.*

import androidx.annotation.IdRes
import com.roughike.bottombar.OnTabSelectListener
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.smartchat.utils.FragmentUtil



import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.smartchat.R.id.tab_message
import com.hyphenate.EMConnectionListener
import com.hyphenate.EMError
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import org.jetbrains.anko.startActivity


class MainActivity : BaseActivity() {

    val messageListener = object :EMMessageListener{
        override fun onMessageRecalled(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageChanged(p0: EMMessage?, p1: Any?) {

        }

        override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            runOnUiThread { updateBottomBarOnReadCount() }
        }

        override fun onMessageDelivered(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageRead(p0: MutableList<EMMessage>?) {

        }

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initListener() {


        bottomBar.setOnTabSelectListener { tabId ->

            val transaction =  supportFragmentManager.beginTransaction()
            FragmentUtil.fragmentUtil.getFragment(tabId)?.let {
                transaction.replace(R.id.containner,
                    it
                )
            }
            transaction.commit()
        }

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
        EMClient.getInstance().addConnectionListener(object :EMConnectionListener{
            override fun onConnected() {

            }

            override fun onDisconnected(p0: Int) {
                if(p0==EMError.USER_LOGIN_ANOTHER_DEVICE){
                    //多设备登录
                    startActivity<LoginActivity>()
                    finish()
                }

            }

        })
    }

    override fun onResume() {
        super.onResume()
        updateBottomBarOnReadCount()
    }

    private fun updateBottomBarOnReadCount() {

        val tabWithId = bottomBar.getTabWithId(R.id.tab_message)
        tabWithId.setBadgeCount(EMClient.getInstance().chatManager().unreadMessageCount)
    }

    override fun onDestroy() {
        super.onDestroy()
        EMClient.getInstance().chatManager().removeMessageListener(messageListener)
    }
}
