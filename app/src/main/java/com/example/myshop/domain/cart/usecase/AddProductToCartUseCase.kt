package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend operator fun invoke(productId: String, amount: Amount) {
        cartRepository.addToCart(productId, amount)

    }
}
