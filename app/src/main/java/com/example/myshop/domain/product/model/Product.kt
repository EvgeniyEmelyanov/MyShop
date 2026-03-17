package com.example.myshop.domain.product.model

import com.example.myshop.domain.common.Money

data class Product(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val imageKey: String,
    val price: Money,
    val amountType: AmountType,
    val pricingUnit: PricingUnit,
    val tags: Set <ProductTag>
)