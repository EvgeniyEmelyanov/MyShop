package com.example.myshop.features.deliveryAddress.presentation

import com.example.myshop.domain.deliveryAddress.DeliveryAddressType

data class DeliveryAddressUiModel(
    val id: Long,
    val type: DeliveryAddressType,
    val settlement: String,
    val street: String,
    val house: String,
    val building: String?,
    val apartment: String?,
    val isDefault: Boolean
)
