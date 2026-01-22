package com.example.myshop

data class ProductDetailUiState(
    val title: String,
    val weight: String,
    val description: String,
    val imageRes: Int,
    val countText: String,
    val priceText: String,
    val isInCart: Boolean,
    val addButtonText: String,
    val isAddEnabled: Boolean,
    val isFavorite: Boolean,
    val isDescriptionExpanded: Boolean
)