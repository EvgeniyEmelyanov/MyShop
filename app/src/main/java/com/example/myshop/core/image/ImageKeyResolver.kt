package com.example.myshop.core.ui.image

import androidx.annotation.DrawableRes
import com.example.myshop.R

object ImageKeyResolver { // временно делаем обджектом пока не перевели все на архитектуру.

    @DrawableRes
    fun resolve(imageKey: String): Int =
        when (imageKey) {
            "apple_picture" -> R.drawable.apple_picture
            "banana_picture" -> R.drawable.banana_picture
            "pepper_picture" -> R.drawable.pepper_picture
            // добавляй дальше…
            else -> R.drawable.apple_picture // сделай плейсхолдер
        }
}