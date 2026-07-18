package com.example.myshop.features.orders.presentation

import com.example.myshop.core.ui.ContentState

data class OrdersUiState(
    val orders: List<OrderUiModel> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
    )
