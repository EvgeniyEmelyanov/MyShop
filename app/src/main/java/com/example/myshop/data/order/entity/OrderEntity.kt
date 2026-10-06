package com.example.myshop.data.order.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity (
    @PrimaryKey
    val id: String,
    val createdAtMillis: Long,
    val status: String,
    val totalCents: Long,
    val currency: String,

    val fulfillmentType: String,
    val deliveryAddressType: String?,
    val deliverySettlement: String?,
    val deliveryStreet: String?,
    val deliveryHouse: String?,
    val deliveryBuilding: String?,
    val deliveryApartment: String?
)