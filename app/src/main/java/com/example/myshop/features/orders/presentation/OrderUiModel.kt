package com.example.myshop.features.orders.presentation

data class OrderUiModel(
    val id: String,
    val date: String,
    val time: String,
    val statusFilter: OrdersFilter,
    val status: String,
    val productImages: List<Int>,
    val itemsCount: String,
    val total: String
)
