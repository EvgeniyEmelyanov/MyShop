package com.example.myshop.domain.order.model

import com.example.myshop.domain.deliveryAddress.DeliveryAddressType

sealed interface OrderFulfillment {
    data object Pickup : OrderFulfillment

    data class Delivery(
        val type: DeliveryAddressType,
        val settlement: String,
        val street: String,
        val house: String,
        val building: String?,
        val apartment: String?
    ) : OrderFulfillment
}