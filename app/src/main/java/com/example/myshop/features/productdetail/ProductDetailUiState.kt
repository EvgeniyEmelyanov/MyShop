package com.example.myshop.features.productdetail

data class ProductDetailUiState(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val price: String,
    val countText: String,
    val imageRes: Int,
    val isFavorite: Boolean,
    val isCart: Boolean,
    val addButtonText: String,
    val isAddEnabled: Boolean,
    val isDescriptionExpanded: Boolean
)
