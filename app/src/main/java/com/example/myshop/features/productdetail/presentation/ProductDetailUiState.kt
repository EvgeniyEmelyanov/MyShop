package com.example.myshop.features.productdetail.presentation

data class ProductDetailUiState(
    val id: String = "",
    val title: String = "",
    val subtitle: String = "",
    val description: String = "",
    val price: String = "",
    val countText: String = "",
    val imageRes: Int = 0,
    val isFavorite: Boolean = false,
    val isCart: Boolean = false,
    val addButtonText: String = "",
    val isAddEnabled: Boolean = true,
    val isDescriptionExpanded: Boolean = false,
    val isLoading: Boolean = false
)