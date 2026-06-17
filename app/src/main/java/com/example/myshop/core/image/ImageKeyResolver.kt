package com.example.myshop.core.image

import androidx.annotation.DrawableRes
import com.example.myshop.R
import javax.inject.Inject
class ImageKeyResolver @Inject constructor() {

    @DrawableRes
    fun resolve(imageKey: String): Int =
        when (imageKey) {
            "apple_picture" -> R.drawable.img_product_apple
            "banana_picture" -> R.drawable.img_product_banana
            "pepper_picture" -> R.drawable.img_product_pepper
            else -> R.drawable.img_product_apple
        }
}
