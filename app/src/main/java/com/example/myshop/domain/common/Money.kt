package com.example.myshop.domain.common

import com.example.myshop.domain.product.model.Currency

data class Money(
    val cents: Long,
    val currency: Currency
)