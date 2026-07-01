package com.example.myshop.data.cart.repository

import com.example.myshop.data.cart.local.dao.CartDao
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.data.cart.local.mapper.toDomain
import com.example.myshop.data.cart.local.mapper.toEntity
import com.example.myshop.data.cart.local.mapper.typeAndValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CartRepositoryImpl @Inject constructor(private val cartDao: CartDao) : CartRepository {


    override suspend fun getCart(): Cart {
        val items = cartDao.getAll().map { entity ->
            entity.toDomain()
        }
        return Cart(items)
    }

    override fun observeCart(): Flow<Cart> {
        return cartDao.observeAll().map { entities ->
            Cart(entities.map { entity -> entity.toDomain() })
        }
    }


    override suspend fun addToCart(
        productId: String,
        amount: Amount
    ) {
        val existingItem = cartDao.getByProductId(productId)

        if (existingItem != null) {
            updateAmount(productId, amount)
            return
        }

        val nextSortOrder = (cartDao.getMaxSortOrder() ?: 0L) + 1L
        cartDao.insert(amount.toEntity(productId, nextSortOrder))
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
        updateAmount(productId, amount)
    }

    private suspend fun updateAmount(
        productId: String,
        amount: Amount
    ) {
        val (amountType, amountValue) = amount.typeAndValue()

        cartDao.updateAmount(
            productId = productId,
            amountType = amountType,
            amountValue = amountValue
        )
    }
}
