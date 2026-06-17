package com.example.myshop.features.cart.presentation

import com.example.myshop.core.ui.ContentState

data class CartUiState(
    val items: List<CartUiModel> = emptyList(),
    val totalString: String = "0.00 $",
    val contentState: ContentState = ContentState.LOADING

)
