package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import javax.inject.Inject
class IncreaseAmountUseCase @Inject constructor(private val cartRepository: CartRepository) {

   suspend fun increaseAmount(productId: String) {
        val cart = cartRepository.getCart()
        val item = cart.items.find { it.productId == productId } ?: return

        when (item.amount) {
            is Amount.Piece -> {
                cartRepository.setAmount(productId, Amount.Piece(item.amount.count + 1))
            }

            is Amount.Grams -> {
                cartRepository.setAmount(productId, Amount.Grams(item.amount.grams + 20L))
            }
        }
    }
}
