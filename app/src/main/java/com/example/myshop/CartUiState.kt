package com.example.myshop

data class CartUiState(
    val items: List<CartUiModel> = emptyList(),
    val totalString: String = "0.00 $"
)