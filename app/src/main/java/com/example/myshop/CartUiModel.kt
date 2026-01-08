package com.example.myshop

data class CartUiModel(
    val productId: String,
    val title: String,
    val imageRes: Int,
    val weightText: String,
    val quantityText: String,
    val lineTotalText: String
)
