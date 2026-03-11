package com.example.myshop.core.ui.formatter

import com.example.myshop.domain.cart.model.Amount

class QuantityFormatter() {

    fun quantityFormat(amount: Amount): String {
        val quantity = when (amount) {
            is Amount.Piece -> "${amount.count} pcs"
            is Amount.Grams -> "${amount.grams} g"
        }
        return quantity
    }
}