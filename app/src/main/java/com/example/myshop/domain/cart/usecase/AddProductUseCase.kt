package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount

class AddProductUseCase(private val cartRepository: CartRepository) {

    fun addProduct(productId: String, amount: Amount) {
        cartRepository.addProduct(productId, amount)

    }
}