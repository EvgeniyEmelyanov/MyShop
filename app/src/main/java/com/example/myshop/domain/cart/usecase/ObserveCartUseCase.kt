package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Cart
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(): Flow<Cart> {
        return cartRepository.observeCart()
    }

}