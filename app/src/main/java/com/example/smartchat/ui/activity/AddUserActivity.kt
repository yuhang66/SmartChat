package com.example.smartchat.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartchat.R
import kotlinx.android.synthetic.main.titlebar.*

class AddUserActivity : BaseActivity() {
    override fun getLayout(): Int {
       return R.layout.activity_add_user
      }

    override fun initData() {
        title_text.text = getString(R.string.add_user)

    }



}
