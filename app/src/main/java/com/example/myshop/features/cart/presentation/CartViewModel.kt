package com.example.myshop.features.cart.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.RemoveProductUseCase
import com.example.myshop.domain.cart.usecase.SetAmountUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val setAmountUseCase: SetAmountUseCase,
    private val removeProductUseCase: RemoveProductUseCase,
    private val clearProductsUseCase: ClearProductsUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val quantityFormatter: QuantityFormatter,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val moneyFormatter: MoneyFormatter
) : ViewModel() {

    private val _state = MutableLiveData(CartUiState())
    val state: LiveData<CartUiState> = _state

    private val _orderPlacedEvent = MutableLiveData(false)
    val orderPlacedEvent: LiveData<Boolean> = _orderPlacedEvent

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun addProduct(productId: String, amount: Amount) {
        viewModelScope.launch {
            addProductToCartUseCase(productId, amount)
            reloadState()
        }
    }

    fun increaseAmount(productId: String) {
        viewModelScope.launch {
            increaseAmountUseCase.increaseAmount(productId)
            reloadState()
        }
    }

    fun decreaseAmount(productId: String) {
        viewModelScope.launch {
            decreaseAmountUseCase.decreaseAmount(productId)
            reloadState()
        }
    }

    fun removeProduct(productId: String) {
        viewModelScope.launch {
            removeProductUseCase.removeProduct(productId)
            reloadState()
        }
    }

    fun clearProducts() {
        viewModelScope.launch {
            clearProductsUseCase.clearProducts()
            reloadState()
        }
    }

    fun placeOrder() {
        viewModelScope.launch {
            clearProductsUseCase.clearProducts()
            reloadState()
            _orderPlacedEvent.value = true
        }
    }

    fun orderPlacedEventHandled() {
        _orderPlacedEvent.value = false
    }

    fun setAmount(productId: String, amount: Amount) {
        viewModelScope.launch {
            setAmountUseCase.setAmount(productId, amount)
            reloadState()
        }
    }

    private suspend fun reloadState() {
        val currentState = _state.value ?: CartUiState()
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

    private suspend fun buildState(): CartUiState {
        val cart = getCartUseCase()
        val totals = calculateCartTotalsUseCase.execute()
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
