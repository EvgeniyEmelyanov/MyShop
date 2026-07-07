package com.example.myshop.features.favourite.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.usecase.AddAllFavouriteToCartUseCase
import com.example.myshop.domain.favourite.model.Favourite
import com.example.myshop.domain.favourite.usecase.ObserveFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val observeFavouriteUseCase: ObserveFavouriteUseCase,
    private val addAllFavouriteToCartUseCase: AddAllFavouriteToCartUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter

) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteUiState())
    val state = _state.asStateFlow()
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private var observeFavouriteJob: Job? = null

    init {
        observeFavourite()
    }

    fun load() {
        _state.value = _state.value.copy(contentState = ContentState.LOADING)
        observeFavourite()
    }

    fun onAddAllToCart() {
        viewModelScope.launch {
            val addedCount = addAllFavouriteToCartUseCase.addAll()

            _toastMessage.emit(
                when (addedCount) {
                    0 -> "All favourite items are already in cart"
                    1 -> "1 item added to cart"
                    else -> "$addedCount items added to cart"
                }
            )
        }
    }

    private fun observeFavourite() {
        observeFavouriteJob?.cancel()

        observeFavouriteJob = viewModelScope.launch {
            observeFavouriteUseCase().catch { error ->
                if (error is CancellationException) {
                    throw error
                }
                _state.value = _state.value.copy(
                    contentState = ContentState.ERROR
                )
            }.collect { favourite ->
                updateState(favourite)
            }
        }

    }

    private suspend fun updateState(favourite: Favourite) {
        val currentState = _state.value

        try {
            val newState = buildState(favourite)

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

    private suspend fun buildState(favourite: Favourite): FavouriteUiState {

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
