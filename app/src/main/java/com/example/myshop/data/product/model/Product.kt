package com.example.myshop.data.product.model


data class Product(
    val id: String,
    val title: String,
    val weight: String,
    val price: String,
    val imageKey: String,
    val productDescription: String,
    val unit: ProductUnit,
    val tags: Set<ProductTag> = emptySet(),
    val category: Category
)
