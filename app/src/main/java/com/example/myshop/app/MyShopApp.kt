package com.example.myshop.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyShopApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}