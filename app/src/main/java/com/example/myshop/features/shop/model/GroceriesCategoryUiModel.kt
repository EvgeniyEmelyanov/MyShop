package com.example.myshop.features.shop.model

import androidx.annotation.ColorRes

data class GroceriesCategoryUiModel(
    val title: String,
    val imageRes: Int,
    @ColorRes val backgroundColorRes: Int
)