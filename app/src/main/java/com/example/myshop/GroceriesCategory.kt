package com.example.myshop

import androidx.annotation.ColorRes

data class GroceriesCategory(
    val title: String,
    val imageRes: Int,
   @ColorRes val backgroundColorRes: Int
)
