package com.example.myshop.domain.deliveryAddress

data class DeliveryAddress(
    val id: Long,
    val type: DeliveryAddressType,
    val settlement: String,
    val street: String,
    val house: String,
    val building: String?,
    val apartment: String?,
    val isDefault: Boolean
)

