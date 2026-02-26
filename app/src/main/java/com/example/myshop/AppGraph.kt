package com.example.myshop

import com.example.myshop.data.cart.repository.CartRepositoryImpl
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.usecase.AddProductUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.RemoveProductUseCase
import com.example.myshop.domain.cart.usecase.SetAmountUseCase

object AppGraph {

    val cartRepository: CartRepository = CartRepositoryImpl()
    val getCartUseCase = GetCartUseCase(cartRepository)
    val addProductUseCase = AddProductUseCase(cartRepository)
    val setAmountUseCase = SetAmountUseCase(cartRepository)
    val removeProductUseCase = RemoveProductUseCase(cartRepository)
    val clearProductsUseCase = ClearProductsUseCase(cartRepository)
    val increaseAmountUseCase = IncreaseAmountUseCase(cartRepository)
    val decreaseAmountUseCase = DecreaseAmountUseCase(cartRepository)
}