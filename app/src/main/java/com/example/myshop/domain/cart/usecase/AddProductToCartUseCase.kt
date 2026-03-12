package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount

class AddProductToCartUseCase(private val cartRepository: CartRepository) {

    fun addProduct(productId: String, amount: Amount) {
        cartRepository.addToCart(productId, amount)

    }
}