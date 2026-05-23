package com.example.myshop.data.product.remote.model

data class ProductDto(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val imageKey: String,
    val priceCents: Long,
    val amountType: String,
    val pricingUnit: String,
    val tags: List<String>,
    val category: String
)