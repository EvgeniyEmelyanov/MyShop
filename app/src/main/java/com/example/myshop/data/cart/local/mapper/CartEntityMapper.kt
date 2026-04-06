package com.example.myshop.data.cart.local.mapper

import com.example.myshop.data.cart.local.entity.CartItemEntity
import com.example.myshop.domain.cart.model.Amount

fun Amount.toEntity (productId: String): CartItemEntity {
    return when (this) {
        is Amount.Piece -> CartItemEntity(
            productId = productId,
            amountType = "PIECE",
            amountValue = count
        )

        is Amount.Grams -> CartItemEntity(
            productId = productId,
            amountType = "GRAMS",
            amountValue = grams
        )
    }
}