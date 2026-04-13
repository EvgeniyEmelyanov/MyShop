package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Cart
import javax.inject.Inject
class GetCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend fun getCart(): Cart {
        return cartRepository.getCart()
    }
}

