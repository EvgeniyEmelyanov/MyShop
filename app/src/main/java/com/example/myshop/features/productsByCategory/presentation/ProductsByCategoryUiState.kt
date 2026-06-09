package com.example.myshop.features.productsByCategory.presentation

import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.ui.CommonProductUiModel

data class ProductsByCategoryUiState(
    val products: List<CommonProductUiModel> = emptyList(),
    val filterParams: FilterParams = FilterParams(),
    val isLoading: Boolean = false
)