package com.example.smartchat.contract

interface UserContract {
    interface Presenter:BasePresenter{
        fun loadContracts()
    }

    interface View{
        fun loadSuccess()
        fun loadError()
    }
}