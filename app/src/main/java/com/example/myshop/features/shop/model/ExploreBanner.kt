package com.example.myshop.features.shop.model

import androidx.annotation.ColorRes
import com.example.myshop.data.product.model.Category

data class ExploreBanner(
    val image: Int,
    val title: String,
    @ColorRes val backgroundColorRes: Int,
    @ColorRes val strokeColorRes: Int,
    val category: Category

)