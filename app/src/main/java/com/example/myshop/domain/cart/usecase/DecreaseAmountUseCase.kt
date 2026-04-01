package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount

class DecreaseAmountUseCase(private val cartRepository: CartRepository) {

    suspend fun decreaseAmount(productId: String) {
        val cart = cartRepository.getCart()
        val item = cart.items.find { it.productId == productId } ?: return

        when (item.amount) {
            is Amount.Piece -> {
                if (item.amount.count == 1L) {
                    cartRepository.removeProduct(productId)
                } else {
                    cartRepository.setAmount(productId, Amount.Piece(item.amount.count - 1))
                }
            }

            is Amount.Grams -> {
                if (item.amount.grams <= 20L) {
                    cartRepository.removeProduct(productId)
                } else {
                    cartRepository.setAmount(productId, Amount.Grams(item.amount.grams - 20L))
                }
            }
        }
    }
}