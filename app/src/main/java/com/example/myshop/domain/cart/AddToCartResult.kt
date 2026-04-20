package com.example.myshop.domain.cart

sealed interface AddToCartResult {
    data class Added(val productTitle: String) : AddToCartResult
    data class AlreadyInCart(val productTitle: String) : AddToCartResult
    data object ProductNotFound : AddToCartResult
}