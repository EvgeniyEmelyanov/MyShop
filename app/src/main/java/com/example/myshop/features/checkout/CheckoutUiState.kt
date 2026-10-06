package com.example.myshop.features.checkout

import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.order.model.FulfillmentSelection

data class CheckoutUiState(
    val addresses: List<DeliveryAddress> = emptyList(),
    val selection: FulfillmentSelection? = null,
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
