package com.example.myshop.data.cart.local.mapper

import com.example.myshop.data.cart.local.entity.CartItemEntity
import com.example.myshop.domain.cart.model.Amount

fun Amount.toEntity(
    productId: String,
    sortOrder: Long
): CartItemEntity {
    return when (this) {
        is Amount.Piece -> CartItemEntity(
            productId = productId,
            amountType = "PIECE",
            amountValue = count,
            sortOrder = sortOrder
        )

        is Amount.Grams -> CartItemEntity(
            productId = productId,
            amountType = "GRAMS",
            amountValue = grams,
            sortOrder = sortOrder
        )
    }
}

fun Amount.typeAndValue(): Pair<String, Long> {
    return when (this) {
        is Amount.Piece -> "PIECE" to count
        is Amount.Grams -> "GRAMS" to grams
    }
}
