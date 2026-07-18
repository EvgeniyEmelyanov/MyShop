package com.example.myshop.domain.order.model

import kotlin.random.Random

enum class OrderStatus {
    PROCESSING,
    COMPLETED,
    CANCELED
}

// Fake local status until backend/admin panel exists.
fun randomOrderStatus(): OrderStatus {
    val chance = Random.nextInt(1, 101)

    return when {
        chance <= 70 -> OrderStatus.COMPLETED
        chance <= 90 -> OrderStatus.PROCESSING
        else -> OrderStatus.CANCELED
    }
}

