package com.example.myshop.data.order.mapper

import com.example.myshop.data.order.entity.OrderItemEntity
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.order.model.OrderItem
import com.example.myshop.domain.product.model.Currency

fun OrderItem.toEntity(orderId: String): OrderItemEntity {
    val amountType: String
    val amountValue: Long

    when (amount) {
        is Amount.Piece -> {
            amountType = "PIECE"
            amountValue = amount.count
        }

        is Amount.Grams -> {
            amountType = "GRAMS"
            amountValue = amount.grams
        }
    }

    return OrderItemEntity(
        orderId = orderId,
        productId = productId,
        title = title,
        subtitle = subtitle,
        imageKey = imageKey,
        amountType = amountType,
        amountValue = amountValue,
        lineTotalCents = lineTotal.cents,
        currency = lineTotal.currency.name
    )
}

fun OrderItemEntity.toDomain(): OrderItem {
    val amount = when (amountType) {
        "PIECE" -> Amount.Piece(amountValue)
        "GRAMS" -> Amount.Grams(amountValue)
        else -> error("Unknown order item amount type: $amountType")
    }

    return OrderItem(
        productId = productId,
        title = title,
        subtitle = subtitle,
        imageKey = imageKey,
        amount = amount,
        lineTotal = Money(lineTotalCents, Currency.valueOf(currency))
    )
}
