package com.example.myshop.app

import android.app.Application
import com.example.myshop.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyShopApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.MAPKIT_API_KEY.isNotBlank()) {
            MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
            MapKitFactory.initialize(this)
        }
    }
}
