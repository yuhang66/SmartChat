package com.example.smartchat.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    val progressDialog by lazy { ProgressDialog(this) }

    val inputMethodManager by lazy { //隐藏软键盘
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initData()
        initListener()
    }

    open protected fun initListener() {

    }

    open protected fun initData() {

    }

    abstract fun getLayout(): Int

    open fun showProgress(content:String){
        progressDialog.setMessage(content)
        progressDialog.show()
    }

    open fun dismissProgress(){
        progressDialog.dismiss()
    }

    open fun hideKeyBord(){
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
    }
}