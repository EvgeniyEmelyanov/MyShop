package com.example.myshop.domain.cart.service

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.product.model.PricingUnit
import javax.inject.Inject

class LinePriceCalculator @Inject constructor() {

    operator fun invoke(
        priceCents: Long,
        pricingUnit: PricingUnit,
        amount: Amount
    ): Long = when (pricingUnit) {

        PricingUnit.PER_ITEM -> {
            val count = (amount as? Amount.Piece)?.count ?: 0
            priceCents * count
        }

        PricingUnit.PER_KG -> {
            val grams = (amount as? Amount.Grams)?.grams ?: 0
            (priceCents * grams) / 1000L
        }
    }
}
