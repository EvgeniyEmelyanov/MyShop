package com.example.myshop.features.productdetail.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Amount.*
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.*
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.formatter.QuantityFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.favourite.usecase.IsFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val quantityFormatter: QuantityFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val linePriceCalculator: LinePriceCalculator,
    private val imageKeyResolver: ImageKeyResolver,
    private val isFavouriteUseCase: IsFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase,
    private val defaultCartAmountFactory: DefaultCartAmountFactory
) : ViewModel() {

    private var productId: String? = null
    private var selectedAmountPreview: Amount? = null
    private var isDescriptionExpanded: Boolean = false

    private val _state = MutableStateFlow(ProductDetailUiState())
    val state = _state.asStateFlow()

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun setProductId(id: String) {
        if (productId != null && productId != id) return
        productId = id
        load()
    }

    fun onAddToFavorite() {
        viewModelScope.launch {
            val id = productId ?: return@launch
            toggleFavouriteUseCase.toggle(id)
            load()
        }
    }

    fun onToggleDescription() {
        isDescriptionExpanded = !isDescriptionExpanded
        load()
    }

    fun onPlus() {
        val id = productId ?: return
        viewModelScope.launch {
            val cartItem = getCartUseCase().items.find { it.productId == id }

            if (cartItem != null) {
                increaseAmountUseCase.increaseAmount(id)
            } else {
                val product = getProductByIdUseCase(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

                selectedAmountPreview = when (cur) {
                    is Piece -> Piece(cur.count + 1)
                    is Grams -> Grams(cur.grams + 20)
                }
            }
            reloadState()
        }

    }

    fun onMinus() {
        val id = productId ?: return

        viewModelScope.launch {
            val cartItem = getCartUseCase().items.find { it.productId == id }

            if (cartItem != null) {
                decreaseAmountUseCase.decreaseAmount(id)
            } else {
                val product = getProductByIdUseCase(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

                selectedAmountPreview = when (cur) {
                    is Piece -> Piece(maxOf(1, cur.count - 1))
                    is Grams -> Grams(maxOf(20, cur.grams - 20))
                }
            }
            reloadState()
        }

    }

    fun onAddToCart() {
        viewModelScope.launch {
            val id = productId ?: return@launch
            val product = getProductByIdUseCase(id) ?: return@launch
            val amount = selectedAmountPreview ?: defaultCartAmountFactory(product.amountType)

            when (addProductToCartIfAbsentUseCase(id, amount)) {
                is AddToCartResult.Added -> reloadState()
                is AddToCartResult.AlreadyInCart -> Unit
                AddToCartResult.ProductNotFound -> Unit
            }
        }
    }

    private suspend fun reloadState() {
        val id = productId ?: return

        val currentState = _state.value

        _state.value = currentState.copy(contentState = ContentState.LOADING)

        try {
            val newState = buildState(id)
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

    private suspend fun buildState(id: String): ProductDetailUiState {
        val product = getProductByIdUseCase(id) ?: error("Product not found")

        val cart = getCartUseCase()
        val realItem = cart.items.find { it.productId == id }
        val inCart = realItem != null
        val addButtonText = if (inCart) "Added" else "Add to cart"
        val isAddEnabled = !inCart

        val isFavourite = isFavouriteUseCase.isFavourite(id)

        val imageRes = imageKeyResolver.resolve(product.imageKey)

        val amountToShow = realItem?.amount ?: selectedAmountPreview ?: defaultCartAmountFactory(
            product.amountType
        )

        val countText = quantityFormatter.quantityFormat(amountToShow)

        // цена строки: считаем totals один раз и берём lineTotals[id]
        val priceText = if (inCart) {
            val totals = calculateCartTotalsUseCase.execute()
            val lineMoney = totals.lineTotals[id]
            lineMoney?.let(moneyFormatter::format) ?: "—"
        } else {
            val lineCents = linePriceCalculator.calculateLineCents(
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


