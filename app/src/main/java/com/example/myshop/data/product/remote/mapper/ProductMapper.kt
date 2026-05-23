package com.example.myshop.data.product.remote.mapper

import com.example.myshop.data.product.remote.model.ProductDto
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag


fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        subtitle = subtitle,
        description = description,
        imageKey = imageKey,
        price = Money(priceCents, Currency.USD),
        amountType = AmountType.valueOf(amountType),
        pricingUnit = PricingUnit.valueOf(pricingUnit),
        tags = tags.map { ProductTag.valueOf(it) }.toSet(),
        category = Category.valueOf(category)
    )
}


