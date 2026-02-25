package com.example.myshop.domain.cart

import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart

interface CartRepository {

    fun getCart(): Cart

    fun addProduct(productId: String, amount: Amount)

    fun removeProduct(productId: String)

    fun clearProducts()

    fun setAmount(productId: String, amount: Amount)



}