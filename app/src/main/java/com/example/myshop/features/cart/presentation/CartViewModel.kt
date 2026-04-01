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
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.formatter.QuantityFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import kotlinx.coroutines.launch

class CartViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val getCartUseCase: GetCartUseCase,
    private val quantityFormatter: QuantityFormatter,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val setAmountUseCase: SetAmountUseCase,
    private val removeProductUseCase: RemoveProductUseCase,
    private val clearProductsUseCase: ClearProductsUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val moneyFormatter: MoneyFormatter
) : ViewModel() {

    private val _state = MutableLiveData(CartUiState())
    val state: LiveData<CartUiState> = _state

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun addProduct(productId: String, amount: Amount) {
        viewModelScope.launch {
            addProductToCartUseCase.addProduct(productId, amount)
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

    fun setAmount(productId: String, amount: Amount) {
        viewModelScope.launch {
            setAmountUseCase.setAmount(productId, amount)
            reloadState()
        }
    }

    private suspend fun reloadState() {
        val currentState = _state.value ?: CartUiState()
        _state.value = currentState.copy(isLoading = true)

        val newState = buildState()

        _state.value = newState.copy(isLoading = false)
    }

    private suspend fun buildState(): CartUiState {
        val cart = getCartUseCase.getCart()
        val totals = calculateCartTotalsUseCase.execute()
        val totalString = moneyFormatter.format(totals.total)

        val uiItems = cart.items.mapNotNull { item ->
            val product = getProductByIdUseCase.getById(item.productId) ?: return@mapNotNull null

            val imageRes = imageKeyResolver.resolve(product.imageKey)
            val quantityText = quantityFormatter.quantityFormat(item.amount)
            val lineTotalText = totals.lineTotals[item.productId]
                ?.let(moneyFormatter::format)
                ?: "—"

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
            items = uiItems,
            totalString = totalString
        )
    }
}