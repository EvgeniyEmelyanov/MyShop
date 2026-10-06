package com.example.myshop.features.deliveryAddress.presentation

import com.example.myshop.core.ui.ContentState

data class DeliveryAddressUiState(
    val addresses: List<DeliveryAddressUiModel> = emptyList(),
    val contentState: ContentState = ContentState.LOADING
)