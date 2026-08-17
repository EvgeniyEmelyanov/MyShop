package com.example.myshop.features.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.RemoveProductUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.order.usecase.PlaceOrderUseCase
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
class CartViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val observeCartUseCase: ObserveCartUseCase,
    private val removeProductUseCase: RemoveProductUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val quantityFormatter: QuantityFormatter,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val placeOrderUseCase: PlaceOrderUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state = _state.asStateFlow()
    private val _orderPlacedEvent = MutableSharedFlow<Unit>()
    val orderPlacedEvent = _orderPlacedEvent.asSharedFlow()
    private var observeCartJob: Job? = null

    init {
        observeCart()
    }

    fun load() {
        _state.value = _state.value.copy(contentState = ContentState.LOADING)
        observeCart()
    }

    fun increaseAmount(productId: String) {
        viewModelScope.launch {
            increaseAmountUseCase.increaseAmount(productId)
        }
    }

    fun decreaseAmount(productId: String) {
        viewModelScope.launch {
            decreaseAmountUseCase.decreaseAmount(productId)
        }
    }

    fun removeProduct(productId: String) {
        viewModelScope.launch {
            removeProductUseCase.removeProduct(productId)
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            val order = placeOrderUseCase()

            if (order != null) {
                _orderPlacedEvent.emit(Unit)
            }
        }
    }

    private fun observeCart() {
        observeCartJob?.cancel()

        observeCartJob = viewModelScope.launch {
            observeCartUseCase()
                .catch { error ->
                    if (error is CancellationException) throw error

                    _state.value = _state.value.copy(
                        contentState = ContentState.ERROR
                    )
                }
                .collect { cart ->
                    updateState(cart)
                }
        }
    }

    private suspend fun updateState(cart: Cart) {
        val currentState = _state.value

        try {
            val newState = buildState(cart)

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

    private suspend fun buildState(cart: Cart): CartUiState {
        val totals = calculateCartTotalsUseCase.execute(cart)
        val totalString = moneyFormatter.format(totals.total)

        val uiItems = cart.items.mapNotNull { item ->
            val product = getProductByIdUseCase(item.productId) ?: return@mapNotNull null

            val imageRes = imageKeyResolver.resolve(product.imageKey)
            val quantityText = quantityFormatter.quantityFormat(item.amount)
            val lineTotalText =
                totals.lineTotals[item.productId]?.let(moneyFormatter::format) ?: "—"

            CartUiModel(
                productId = product.id,
                titleText = product.title,
                subtitleText = product.subtitle,
                imageRes = imageRes,
                quantityText = quantityText,
                lineTotalText = lineTotalText
            )
        }

        return CartUiState(
            items = uiItems, totalString = totalString
        )
    }
}
