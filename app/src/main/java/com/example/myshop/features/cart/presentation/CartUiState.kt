package com.example.myshop.features.cart.presentation

data class CartUiState(
    val items: List<CartUiModel> = emptyList(),
    val totalString: String = "0.00 $"
)
