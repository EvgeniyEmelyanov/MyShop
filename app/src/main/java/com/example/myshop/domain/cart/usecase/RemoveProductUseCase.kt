package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository

class RemoveProductUseCase(private val cartRepository: CartRepository) {

    suspend fun removeProduct(productId: String) {
        cartRepository.removeProduct(productId)

    }
}