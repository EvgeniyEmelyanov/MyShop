package com.example.myshop.features.shop.model

import androidx.annotation.ColorRes

data class GroceriesCategory(
    val title: String,
    val imageRes: Int,
   @ColorRes val backgroundColorRes: Int
)