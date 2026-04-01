package com.example.myshop.data.cart.repository

import com.example.myshop.data.cart.local.CartDao
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.data.cart.local.toDomain
import com.example.myshop.data.cart.local.toEntity


class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {


    override suspend fun getCart(): Cart {
        val items = cartDao.getAll().map { entity ->
            entity.toDomain()
        }
        return Cart(items)
    }


    override suspend fun addToCart(
        productId: String,
        amount: Amount
    ) {
        cartDao.insert(amount.toEntity(productId))
    }

    override suspend fun removeProduct(productId: String) {
        cartDao.remove(productId)
    }

    override suspend fun clearProducts() {
        cartDao.clear()
    }

    override suspend fun setAmount(
        productId: String,
        amount: Amount
    ) {
        cartDao.insert(amount.toEntity(productId))
    }
}