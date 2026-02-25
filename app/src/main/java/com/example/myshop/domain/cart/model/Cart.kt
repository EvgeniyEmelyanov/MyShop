package com.example.myshop.domain.cart.model

data class Cart (
    val items: List<CartItem> = emptyList(),
    val totalCents: Money
)

