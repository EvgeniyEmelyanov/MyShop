package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount

class SetAmountUseCase(private val cartRepository: CartRepository) {

    fun setAmount(productId: String, amount: Amount) {
        cartRepository.setAmount(productId, amount)
    }
}