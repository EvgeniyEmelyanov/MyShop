package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Cart

class GetCartUseCase(private val cartRepository: CartRepository) {

    suspend fun getCart(): Cart {
        return cartRepository.getCart()
    }
}
