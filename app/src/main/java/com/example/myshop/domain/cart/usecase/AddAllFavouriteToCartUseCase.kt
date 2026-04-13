package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.product.repository.ProductRepository
import javax.inject.Inject
class AddAllFavouriteToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
    private val favouriteRepository: FavouriteRepository,
    private val productRepository: ProductRepository,
    private val defaultCartAmountFactory: DefaultCartAmountFactory
) {

    suspend fun addAll(): Int {
        val favourite = favouriteRepository.getFavourite()
        val cart = cartRepository.getCart()

        val cartIds = cart.items
            .map { it.productId }
            .toMutableSet()

        var addedCount = 0

        for (item in favourite.items) {
            val productId = item.productId

            if (productId in cartIds) continue

            val product = productRepository.getById(productId) ?: continue

            val amount = defaultCartAmountFactory.defaultAmount(product.amountType)

            cartRepository.addToCart(productId, amount)
            cartIds.add(productId)
            addedCount++
        }

        return addedCount
    }
}
