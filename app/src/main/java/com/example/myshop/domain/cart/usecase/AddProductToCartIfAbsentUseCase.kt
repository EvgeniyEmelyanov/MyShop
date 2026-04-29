package com.example.myshop.domain.cart.usecase

import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import javax.inject.Inject

class AddProductToCartIfAbsentUseCase @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val defaultCartAmountFactory: DefaultCartAmountFactory
) {

    suspend operator fun invoke(productId: String): AddToCartResult {
        val product =
            getProductByIdUseCase(productId) ?: return AddToCartResult.ProductNotFound

        val cart = getCartUseCase()
        val alreadyInCart = cart.items.any { it.productId == productId }

        if (alreadyInCart) {
            return AddToCartResult.AlreadyInCart(product.title)
        }

        val amount = defaultCartAmountFactory(product.amountType)

        addProductToCartUseCase(productId, amount)
        return AddToCartResult.Added(product.title)
    }
}
