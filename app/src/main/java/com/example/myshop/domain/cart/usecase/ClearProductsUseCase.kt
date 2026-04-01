package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository

class ClearProductsUseCase(private val cartRepository: CartRepository) {

    suspend fun clearProducts() {
        cartRepository.clearProducts()
    }
}