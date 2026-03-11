package com.example.myshop.data.cart.repository

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem

class CartRepositoryImpl : CartRepository {

    private val cartList = mutableMapOf<String, Amount>()


    override fun getCart(): Cart {
        val items = cartList.map { (productId, amount) ->
            CartItem(productId, amount)
        }
        return Cart(items)
    }

    override fun addToCart(
        productId: String,
        amount: Amount
    ) {
        cartList[productId] = amount
    }

    override fun removeProduct(productId: String) {
        cartList.remove(productId)
    }

    override fun clearProducts() {
        cartList.clear()
    }

    override fun setAmount(
        productId: String,
        amount: Amount
    ) {
        cartList[productId] = amount
    }

}