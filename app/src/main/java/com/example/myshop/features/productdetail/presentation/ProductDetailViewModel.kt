package com.example.myshop.features.productdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.favourite.usecase.ObserveFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val quantityFormatter: QuantityFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val linePriceCalculator: LinePriceCalculator,
    private val imageKeyResolver: ImageKeyResolver,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase,
    private val defaultCartAmountFactory: DefaultCartAmountFactory,
    private val observeCartUseCase: ObserveCartUseCase,
    private val observeFavouriteUseCase: ObserveFavouriteUseCase
) : ViewModel() {

    private var productId: String? = null
    private var selectedAmountPreview: Amount? = null
    private var isDescriptionExpanded: Boolean = false

    private val _state = MutableStateFlow(ProductDetailUiState())
    val state = _state.asStateFlow()

    private var observeProductJob: Job? = null

    fun load() {
        val id = productId ?: return

        _state.value = _state.value.copy(
            contentState = ContentState.LOADING
        )

        observeProduct(id)
    }

    fun setProductId(id: String) {
        if (productId == id) return

        productId = id
        observeProduct(id)
    }

    fun onAddToFavorite() {
        viewModelScope.launch {
            val id = productId ?: return@launch
            toggleFavouriteUseCase.toggle(id)
        }
    }

    fun onToggleDescription() {
        isDescriptionExpanded = !isDescriptionExpanded

        _state.value = _state.value.copy(
            isDescriptionExpanded = isDescriptionExpanded
        )
    }

    fun onPlus() {
        val id = productId ?: return
        viewModelScope.launch {

            if (_state.value.isCart) {
                increaseAmountUseCase.increaseAmount(id)
            } else {
                val product = getProductByIdUseCase(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

                val newAmount = when (cur) {
                    is Amount.Piece -> Amount.Piece(cur.count + 1)
                    is Amount.Grams -> Amount.Grams(cur.grams + 20)
                }

                selectedAmountPreview = newAmount
                updatePreview(product, newAmount)
            }
        }

    }

    fun onMinus() {
        val id = productId ?: return

        viewModelScope.launch {

            if (_state.value.isCart) {
                decreaseAmountUseCase.decreaseAmount(id)
            } else {
                val product = getProductByIdUseCase(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

                val newAmount = when (cur) {
                    is Amount.Piece -> Amount.Piece(maxOf(1, cur.count - 1))
                    is Amount.Grams -> Amount.Grams(maxOf(20, cur.grams - 20))
                }

                selectedAmountPreview = newAmount
                updatePreview(product, newAmount)
            }
        }

    }

    fun onAddToCart() {
        viewModelScope.launch {
            val id = productId ?: return@launch
            val product = getProductByIdUseCase(id) ?: return@launch
            val amount = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

            addProductToCartIfAbsentUseCase(id, amount)
        }
    }

    private fun updatePreview(
        product: Product, amount: Amount
    ) {
        val lineCents = linePriceCalculator(
            priceCents = product.price.cents, pricingUnit = product.pricingUnit, amount = amount
        )

        _state.value = _state.value.copy(
            countText = quantityFormatter.quantityFormat(amount),
            price = moneyFormatter.format(Money(lineCents, Currency.USD))
        )
    }

    private fun observeProduct(id: String) {
        observeProductJob?.cancel()

        observeProductJob = viewModelScope.launch {
            combine(
                observeCartUseCase(), observeFavouriteUseCase()
            ) { cart, favourite ->
                cart to favourite
            }.catch { error ->
                if (error is CancellationException) {
                    throw error
                }

                _state.value = _state.value.copy(
                    contentState = ContentState.ERROR
                )
            }.collect { (cart, favourite) ->
                    val isFavourite = favourite.items.any { item ->
                        item.productId == id
                    }

                    updateState(id, cart, isFavourite)
                }
        }
    }

    private suspend fun updateState(
        id: String, cart: Cart, isFavourite: Boolean
    ) {
        val currentState = _state.value

        try {
            val newState = buildState(id, cart, isFavourite)
            _state.value = newState.copy(contentState = ContentState.CONTENT)
        } catch (error: Exception) {
            if (error is CancellationException) {
                throw error
            }

            _state.value = currentState.copy(
                contentState = ContentState.ERROR
            )
        }
    }

    private suspend fun buildState(
        id: String, cart: Cart, isFavourite: Boolean
    ): ProductDetailUiState {
        val product = getProductByIdUseCase(id) ?: error("Product not found")

        val realItem = cart.items.find { it.productId == id }
        val inCart = realItem != null
        val addButtonText = if (inCart) "Added" else "Add to cart"
        val isAddEnabled = !inCart

        val imageRes = imageKeyResolver.resolve(product.imageKey)

        val amountToShow = realItem?.amount ?: selectedAmountPreview ?: defaultCartAmountFactory(
            product.amountType
        )

        val countText = quantityFormatter.quantityFormat(amountToShow)

        // цена строки: считаем totals один раз и берём lineTotals[id]
        val priceText = if (inCart) {
            val totals = calculateCartTotalsUseCase.execute(cart)
            val lineMoney = totals.lineTotals[id]
            lineMoney?.let(moneyFormatter::format) ?: "—"
        } else {
            val lineCents = linePriceCalculator(
                priceCents = product.price.cents,
                pricingUnit = product.pricingUnit,
                amount = amountToShow
            )
            moneyFormatter.format(Money(lineCents, Currency.USD))

        }
        return ProductDetailUiState(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            description = product.description,
            price = priceText,
            imageRes = imageRes,
            isFavorite = isFavourite,
            isCart = inCart,
            countText = countText,
            addButtonText = addButtonText,
            isDescriptionExpanded = isDescriptionExpanded,
            isAddEnabled = isAddEnabled
        )
    }

}


