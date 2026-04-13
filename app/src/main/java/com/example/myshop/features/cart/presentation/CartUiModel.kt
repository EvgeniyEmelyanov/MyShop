package com.example.myshop.features.cart.presentation

data class CartUiModel(
    val productId: String,
    val titleText: String,
    val subtitleText: String,
    val imageRes: Int,
    val quantityText: String,
    val lineTotalText: String
)