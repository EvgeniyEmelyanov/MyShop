package com.example.myshop.domain.cart

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun getCart(): Cart

    fun observeCart(): Flow<Cart>

    suspend fun addToCart(productId: String, amount: Amount)

    suspend fun removeProduct(productId: String)

    suspend fun clearProducts()

    suspend fun setAmount(productId: String, amount: Amount)


}