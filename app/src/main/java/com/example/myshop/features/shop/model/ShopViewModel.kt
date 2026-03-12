package com.example.myshop.features.shop.model

import androidx.lifecycle.ViewModel
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase

class ShopViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
): ViewModel() {



}