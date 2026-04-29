package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Cart
import javax.inject.Inject
class GetCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend operator fun invoke(): Cart {
        return cartRepository.getCart()
    }
}

