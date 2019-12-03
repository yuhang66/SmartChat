package com.example.smartchat.ui.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartchat.R
import com.example.smartchat.adapter.UserAdapter
import com.example.smartchat.contract.UserContract
import com.example.smartchat.presenter.UserPresenter
import com.example.smartchat.ui.activity.AddUserActivity
import com.example.smartchat.ui.activity.ChatRoomActivity
import com.example.smartchat.widget.SlideBar
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.titlebar.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import com.hyphenate.EMContactListener




class UserFragment: BaseFragment(),UserContract.View {

    val presenter = UserPresenter(this)
    override fun initView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_user,null)
    }

    override fun initData() {
        title_text.setText(getString(R.string.linkman))
        title_img.visibility = View.VISIBLE
        title_img.setOnClickListener {
            context!!.startActivity<AddUserActivity>()
        }
        user_refre.apply {
            isRefreshing = true
                setOnRefreshListener {
                    presenter.loadContracts()
                }
        }

        user_recycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = UserAdapter(presenter.userbeanItem)

        }

        slidebar.onSelectionChangedListener = object :SlideBar.OnSectionChangedListener{
            override fun onSlideFinish() {
                hinttext.visibility = View.GONE
            }

            override fun onSectionChanged(fiestLetters: String) {
                hinttext.visibility = View.VISIBLE
                hinttext.text = fiestLetters
                user_recycler.smoothScrollToPosition(getPosition(fiestLetters))//平滑的滑动到具体位置
            }

        }

        presenter.loadContracts()



        EMClient.getInstance().contactManager().setContactListener(object : EMContactListener {
            override fun onFriendRequestAccepted(p0: String?) {

            }

            override fun onFriendRequestDeclined(p0: String?) {

            }



            override fun onContactInvited(username: String, reason: String) {
                //收到好友邀请
            }

            override fun onContactDeleted(username: String) {
                //被删除时回调此方法
                presenter.loadContracts()
            }


            override fun onContactAdded(username: String) {
                //增加了联系人时回调此方法
            }
        })

        (user_recycler.adapter  as UserAdapter).setOnItemChildClickListener { adapter, view, position ->
            val username = presenter.userbeanItem.get(position).userName
            context?.startActivity<ChatRoomActivity>("username" to username)
        }
        (user_recycler.adapter  as UserAdapter).setOnItemChildLongClickListener { adapter, view, position ->
            val username = presenter.userbeanItem.get(position).userName
            val delmessage = String.format(context!!.getString(R.string.user_del),username)
            AlertDialog.Builder(context)
                .setTitle(getString(R.string.holder))
                .setMessage(delmessage)
                .setNegativeButton(R.string.cancle,null)
                .setPositiveButton(R.string.confirm,DialogInterface.OnClickListener { dialog, which ->
                    //删除好友
                    deleteFriend(username)

                })
                .show()
            true
        }

    }

     private fun getPosition(fiestLetters: String): Int =//获取当前位置
        presenter.userbeanItem.binarySearch {
                userbeanItem->userbeanItem.firstLetter.minus(fiestLetters[0])
        }



    override fun loadError() {
        user_refre.isRefreshing = false
        context?.toast(getString(R.string.load_failed))
    }

    override fun loadSuccess() {
        user_refre.isRefreshing = false
        user_recycler.adapter?.notifyDataSetChanged()

    }



    private fun deleteFriend(username: String) {

        EMClient.getInstance().contactManager().aysncDeleteContact(username,object : EMCallBack {
            override fun onSuccess() {
                context!!.runOnUiThread { toast(R.string.del_success) }
            }

            override fun onProgress(p0: Int, p1: String?) {

            }

            override fun onError(p0: Int, p1: String?) {
                context!!.runOnUiThread { toast(R.string.del_fail) }
            }

        })
    }
}