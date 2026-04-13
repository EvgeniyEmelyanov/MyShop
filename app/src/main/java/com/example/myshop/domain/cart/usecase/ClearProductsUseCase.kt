package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import javax.inject.Inject
class ClearProductsUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend fun clearProducts() {
        cartRepository.clearProducts()
    }
}
