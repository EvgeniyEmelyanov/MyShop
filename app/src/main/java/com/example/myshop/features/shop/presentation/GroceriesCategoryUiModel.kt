package com.example.myshop.features.shop.presentation

import androidx.annotation.ColorRes

data class GroceriesCategoryUiModel(
    val title: String,
    val imageRes: Int,
    @ColorRes val backgroundColorRes: Int
)