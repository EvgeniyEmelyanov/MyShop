package com.example.myshop.core.ui

data class CommonProductUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val priceText: String,
    val inCart: Boolean = false
)