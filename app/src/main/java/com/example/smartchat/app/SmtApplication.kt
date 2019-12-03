package com.example.smartchat.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import com.example.smartchat.BuildConfig
import com.example.smartchat.R
import com.example.smartchat.ui.activity.ChatRoomActivity
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.chat.EMTextMessageBody

class SmtApplication : Application() {

    companion object{
        lateinit var instance:SmtApplication
    }

    val soundpool = SoundPool(2,AudioManager.STREAM_MUSIC,0)
    val duan by lazy { soundpool.load(instance, R.raw.dida,0) }
    val long by lazy {soundpool.load(instance,R.raw.dddd,0)  }
    val messageListener = object : EMMessageListener {
        override fun onMessageRecalled(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageChanged(p0: EMMessage?, p1: Any?) {

        }

        override fun onCmdMessageReceived(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            //如果在前台播放短音频
            if(isForeground()){
                soundpool.play(duan,1f,1f,0,0,1f)
            }else{
                soundpool.play(long,1f,1f,0,0,1f)
                showNotifycation(p0)
            }

        }

        override fun onMessageDelivered(p0: MutableList<EMMessage>?) {

        }

        override fun onMessageRead(p0: MutableList<EMMessage>?) {

        }

    }

    private fun showNotifycation(p0: MutableList<EMMessage>?) {//显示通知
        val notifycationmanager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        p0?.forEach {
            var contexttext = getString(R.string.no_text_message)
            if(it.type == EMMessage.Type.TXT){
                contexttext = (it.body as EMTextMessageBody).message

            }
            val intent = Intent(this,ChatRoomActivity::class.java)
            intent.putExtra("username",it.conversationId())

            val taskStackBuilder = TaskStackBuilder.create(this).addParentStack(ChatRoomActivity::class.java).addNextIntent(intent)
            val pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)
            val notifycation = Notification.Builder(this)
                .setContentTitle(getString(R.string.new_message))
                .setContentText(contexttext)
                .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.dash))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.dash)
                .setAutoCancel(true)
                .notification
            notifycationmanager.notify(1,notifycation)
        }

    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions())
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG)

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }
    private fun isForeground():Boolean{
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(runningapp in activityManager.runningAppProcesses){
            if(runningapp.processName == packageName){
               return runningapp.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

}