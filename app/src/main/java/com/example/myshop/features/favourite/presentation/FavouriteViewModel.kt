package com.example.myshop.features.favourite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.domain.favourite.usecase.AddToFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ClearFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.GetFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.RemoveFromFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val clearFavouriteUseCase: ClearFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter

) : ViewModel() {

    private val _state = MutableLiveData(FavouriteUiState())
    val state: LiveData<FavouriteUiState> = _state

     fun load() {
         viewModelScope.launch {
             val currentState= _state.value ?: FavouriteUiState()
             _state.value = currentState.copy(isLoading = true)
             val newState = buildState()
             _state.value = newState.copy(isLoading = false)
         }

    }

    fun onAdd(productId: String) {
        addToFavouriteUseCase.addToFavourite(productId)
        load()

    }

    fun onClear() {
        clearFavouriteUseCase.clearFavourite()
        load()

    }

    fun onRemove(productId: String) {
        removeFromFavouriteUseCase.removeFromFavourite(productId)
        load()

    }

    fun onToggle(productId: String) {
        toggleFavouriteUseCase.toggle(productId)
        load()

    }

    private suspend fun buildState(): FavouriteUiState {
        val favourite = getFavouriteUseCase.getFavouriteItems()

        val uiItems = favourite.items.mapNotNull { item ->
            val product = getProductByIdUseCase.getById(item.productId) ?: return@mapNotNull null

            val imageRes = imageKeyResolver.resolve(product.imageKey)

            val price = moneyFormatter.format(product.price)


            FavouriteUiModel(
                productId = product.id,
                title = product.title,
                subtitle = product.subtitle,
                imageRes = imageRes,
                priceText = price
            )
        }

        return FavouriteUiState(
            items = uiItems
        )

    }
}