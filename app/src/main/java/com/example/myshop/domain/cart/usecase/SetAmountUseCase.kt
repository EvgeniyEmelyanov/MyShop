package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import javax.inject.Inject
class SetAmountUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend fun setAmount(productId: String, amount: Amount) {
        cartRepository.setAmount(productId, amount)
    }
}
