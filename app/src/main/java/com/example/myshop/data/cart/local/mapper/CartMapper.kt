package com.example.myshop.data.cart.local.mapper

import com.example.myshop.data.cart.local.entity.CartItemEntity
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.CartItem

fun CartItemEntity.toDomain(): CartItem {
    val amount = when(amountType){
        "PIECE" -> Amount.Piece(amountValue)
        "GRAMS" -> Amount.Grams(amountValue)
        else -> error("Unknown amount type: $amountType")
    }
    return CartItem(
        productId = productId,
        amount = amount
    )
}