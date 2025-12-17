package com.example.myshop

import androidx.annotation.ColorRes

data class ExploreBanner(
    val image: Int,
    val title: String,
    @ColorRes val backgroundColorRes: Int,
    @ColorRes val strokeColorRes: Int

)
