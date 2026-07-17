package com.example.myshop.domain.order.model

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.common.Money

data class OrderItem(
    val productId: String,
    val title: String,
    val subtitle: String,
    val imageKey: String,
    val amount: Amount,
    val lineTotal: Money
)