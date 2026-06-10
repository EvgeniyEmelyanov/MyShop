package com.example.myshop.features.productsByCategory.presentation

import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState

data class ProductsByCategoryUiState(
    val products: List<CommonProductUiModel> = emptyList(),
    val filterParams: FilterParams = FilterParams(),
    val contentState: ContentState = ContentState.LOADING
)
