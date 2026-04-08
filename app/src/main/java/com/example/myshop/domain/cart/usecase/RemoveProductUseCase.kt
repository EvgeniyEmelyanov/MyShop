package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import javax.inject.Inject
class RemoveProductUseCase @Inject constructor(private val cartRepository: CartRepository) {

    suspend fun removeProduct(productId: String) {
        cartRepository.removeProduct(productId)

    }
}
