package com.example.myshop.core.formatter

import com.example.myshop.domain.cart.model.Amount
import javax.inject.Inject
class QuantityFormatter @Inject constructor() {

    fun quantityFormat(amount: Amount): String {
        val quantity = when (amount) {
            is Amount.Piece -> "${amount.count} pcs"
            is Amount.Grams -> "${amount.grams} g"
        }
        return quantity
    }
}
