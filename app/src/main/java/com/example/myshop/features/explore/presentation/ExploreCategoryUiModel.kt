package com.example.myshop.features.explore.presentation

import androidx.annotation.ColorRes
import com.example.myshop.domain.product.model.Category

data class ExploreCategoryUiModel(
    val image: Int,
    val title: String,
    @ColorRes val backgroundColorRes: Int,
    @ColorRes val strokeColorRes: Int,
    val category: Category

)