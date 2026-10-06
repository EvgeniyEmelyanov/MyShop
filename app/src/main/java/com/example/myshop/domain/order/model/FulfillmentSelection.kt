package com.example.myshop.domain.order.model

sealed interface FulfillmentSelection {
    data object Pickup : FulfillmentSelection

    data class Delivery(
        val addressId: Long
    ) : FulfillmentSelection
}