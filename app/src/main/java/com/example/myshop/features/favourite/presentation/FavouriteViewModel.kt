package com.example.myshop.features.favourite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.usecase.AddAllFavouriteToCartUseCase
import com.example.myshop.domain.favourite.usecase.AddToFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ClearFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.GetFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.RemoveFromFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase,
    private val clearFavouriteUseCase: ClearFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val addAllFavouriteToCartUseCase: AddAllFavouriteToCartUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter

) : ViewModel() {

    private val _state = MutableLiveData(FavouriteUiState())
    val state: LiveData<FavouriteUiState> = _state

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage
    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun onAdd(productId: String) {
        viewModelScope.launch {
            addToFavouriteUseCase.addToFavourite(productId)
            reloadState()
        }
    }

    fun onAddAllToCart() {
        viewModelScope.launch {
            val addedCount = addAllFavouriteToCartUseCase.addAll()

            reloadState()

            _toastMessage.value = when (addedCount) {
                0 -> "All favourite items are already in cart"
                1 -> "1 item added to cart"
                else -> "$addedCount items added to cart"
            }
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clearFavouriteUseCase.clearFavourite()
            reloadState()
        }
    }

    fun onRemove(productId: String) {
        viewModelScope.launch {
            removeFromFavouriteUseCase.removeFromFavourite(productId)
            reloadState()
        }
    }

    fun onToggle(productId: String) {
        viewModelScope.launch {
            toggleFavouriteUseCase.toggle(productId)
            reloadState()
        }
    }

    fun toastShown() {
        _toastMessage.value = null
    }
    private suspend fun reloadState() {
        val currentState = _state.value ?: FavouriteUiState()
        _state.value = currentState.copy(isLoading = true)
        val newState = buildState()
        _state.value = newState.copy(isLoading = false)
    }

    private suspend fun buildState(): FavouriteUiState {
        val favourite = getFavouriteUseCase.getFavourite()

        val uiItems = favourite.items.mapNotNull { item ->
            val product = getProductByIdUseCase(item.productId) ?: return@mapNotNull null

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
