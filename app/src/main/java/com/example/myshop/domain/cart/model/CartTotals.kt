package com.example.myshop.domain.cart.model

import com.example.myshop.domain.common.Money

data class CartTotals(
    val lineTotals: Map<String, Money>,
    val total: Money
)

