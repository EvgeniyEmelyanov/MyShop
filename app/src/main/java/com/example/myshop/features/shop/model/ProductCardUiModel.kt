package com.example.myshop.features.shop.model

import com.example.myshop.domain.product.model.Category

data class ProductCardUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val priceText: String
)
