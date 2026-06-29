package com.example.myshop.features.favourite.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.usecase.AddAllFavouriteToCartUseCase
import com.example.myshop.domain.favourite.usecase.GetFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getFavouriteUseCase: GetFavouriteUseCase,
    private val addAllFavouriteToCartUseCase: AddAllFavouriteToCartUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter

) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteUiState())
    val state = _state.asStateFlow()
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }



    fun onAddAllToCart() {
        viewModelScope.launch {
            val addedCount = addAllFavouriteToCartUseCase.addAll()

            reloadState()

            _toastMessage.emit(
                when (addedCount) {
                    0 -> "All favourite items are already in cart"
                    1 -> "1 item added to cart"
                    else -> "$addedCount items added to cart"
                }
            )
        }
    }

    private suspend fun reloadState() {
        val currentState = _state.value

        _state.value = currentState.copy(contentState = ContentState.LOADING)

        try {
            val newState = buildState()

            if (newState.items.isEmpty()) {
                _state.value = newState.copy(contentState = ContentState.EMPTY)
            } else {
                _state.value = newState.copy(contentState = ContentState.CONTENT)
            }
        } catch (error: Exception) {
            if (error is CancellationException) {
                throw error
            }
            _state.value = currentState.copy(contentState = ContentState.ERROR)
        }
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
