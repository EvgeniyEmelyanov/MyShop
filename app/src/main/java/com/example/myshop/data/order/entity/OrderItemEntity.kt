package com.example.myshop.data.order.entity

import androidx.room.Entity


@Entity(tableName = "order_items", primaryKeys = ["orderId", "productId"])
data class OrderItemEntity(
    val orderId: String,
    val productId: String,
    val title: String,
    val subtitle: String,
    val imageKey: String,
    val amountType: String,
    val amountValue: Long,
    val lineTotalCents: Long,
    val currency: String
)