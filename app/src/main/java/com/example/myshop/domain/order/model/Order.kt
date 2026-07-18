package com.example.myshop.domain.order.model

import com.example.myshop.domain.common.Money

data class Order(
    val id: String,
    val createdAtMillis: Long,
    val status: OrderStatus,
    val items: List<OrderItem>,
    val total: Money
)