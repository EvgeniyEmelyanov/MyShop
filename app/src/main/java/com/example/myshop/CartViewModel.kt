package com.example.myshop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {

    private val _state = MutableLiveData(CartUiState())
    val state: LiveData<CartUiState> = _state

    fun load() {
        _state.value = buildState()
    }

    fun onIncrease(productId: String) {
        AppState.cartManager.increase(productId)
        _state.value = buildState()
    }

    fun onDecrease(productId: String) {
        AppState.cartManager.decrease(productId)
        _state.value = buildState()
    }

    fun onDelete(productId: String) {
        AppState.cartManager.removeItem(productId)
        _state.value = buildState()
    }

    private fun buildState(): CartUiState {
        val cartItems = AppState.cartManager.getItems()

        val uiItems = cartItems.mapNotNull { cartItem ->
            val product = ProductStore.findById(cartItem.productId) ?: return@mapNotNull null

            val quantityText = when (val a = cartItem.amount) {
                is Amount.Pieces -> "${a.count} pcs"
                is Amount.Grams -> "${a.grams} g"
            }

            val cents = AppState.cartManager.lineTotalCents(cartItem)
            val lineTotalText = AppState.cartManager.formatCents(cents)

            CartUiModel(
                productId = product.id,
                titleText = product.title,
                imageRes = product.imageRes,
                weightText = product.weight,
                quantityText = quantityText,
                lineTotalText = lineTotalText
            )
        }

        val totalCents = AppState.cartManager.cartTotalCents()
        val totalText = AppState.cartManager.formatCents(totalCents)

        return CartUiState(
            items = uiItems,
            totalString = totalText
        )
    }
}