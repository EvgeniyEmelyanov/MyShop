package com.example.myshop.app

import android.app.Application
import com.example.myshop.di.AppGraph

class MyShopApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}