package com.example.myshop.domain

import com.example.myshop.domain.AmountType
import com.example.myshop.domain.cart.model.Money

data class Product(
    val id: String,
    val title: String,
    val price: Money,
    val amountType: AmountType

)
