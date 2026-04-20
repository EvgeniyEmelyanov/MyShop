package com.example.myshop.domain.cart.service

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Amount.Grams
import com.example.myshop.domain.cart.model.Amount.Piece
import com.example.myshop.domain.product.model.AmountType
import javax.inject.Inject

class DefaultCartAmountFactory @Inject constructor() {

    operator fun invoke(type: AmountType): Amount = when (type) {
        AmountType.PIECE -> Piece(1)
        AmountType.WEIGHT -> Grams(1000)
    }
}
