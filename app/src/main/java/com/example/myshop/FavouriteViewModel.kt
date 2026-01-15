package com.example.myshop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouriteViewModel : ViewModel() {

    private val _state = MutableLiveData(FavouriteUiState())
    val state: LiveData<FavouriteUiState> = _state
    private fun updateState() {
        _state.value = buildState()
    }

    fun load() {
        updateState()
    }

    fun onRemove(productId: String) {
        AppState.favouriteManager.remove(productId)
        updateState()
    }

    fun onToggle(productId: String) {
        AppState.favouriteManager.toggle(productId)
        updateState()
    }

    private fun buildState(): FavouriteUiState {
        val favouriteItems = AppState.favouriteManager.getAllIds()

        val uiItems = favouriteItems.mapNotNull { favouriteItem ->
            val product = ProductStore.findById(favouriteItem) ?: return@mapNotNull null

            FavouriteUiModel(
                productId = product.id,
                titleText = product.title,
                weightText = product.weight,
                priceText = product.price,
                imageRes = product.imageRes
            )
        }
        return FavouriteUiState(
            items = uiItems
        )

    }
}