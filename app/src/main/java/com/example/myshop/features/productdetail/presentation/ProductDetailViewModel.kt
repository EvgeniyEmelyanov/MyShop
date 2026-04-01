package com.example.myshop.features.productdetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Amount.*
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.*
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.formatter.QuantityFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.domain.favourite.usecase.IsFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import kotlinx.coroutines.launch


class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val setAmountUseCase: SetAmountUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val quantityFormatter: QuantityFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val linePriceCalculator: LinePriceCalculator,
    private val imageKeyResolver: ImageKeyResolver,
    private val isFavouriteUseCase: IsFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) : ViewModel() {

    private var productId: String? = null
    private var selectedAmountPreview: Amount? = null
    private var isDescriptionExpanded: Boolean = false

    private val _state = MutableLiveData(ProductDetailUiState())
    val state: LiveData<ProductDetailUiState> = _state


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
        val id = productId ?: return
        toggleFavouriteUseCase.toggle(id)
        load()
    }

    fun onToggleDescription() {
        isDescriptionExpanded = !isDescriptionExpanded
        load()
    }

    fun onPlus() {
        val id = productId ?: return
        viewModelScope.launch {
            val cartItem = getCartUseCase.getCart().items.find { it.productId == id }

            if (cartItem != null) {
                increaseAmountUseCase.increaseAmount(id)
            } else {
                val product = getProductByIdUseCase.getById(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultPreviewAmount(product.amountType)

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
            val cartItem = getCartUseCase.getCart().items.find { it.productId == id }

            if (cartItem != null) {
                decreaseAmountUseCase.decreaseAmount(id)
            } else {
                val product = getProductByIdUseCase.getById(id) ?: return@launch
                val cur = selectedAmountPreview ?: defaultPreviewAmount(product.amountType)

                selectedAmountPreview = when (cur) {
                    is Piece -> Piece(maxOf(1, cur.count - 1))
                    is Grams -> Grams(maxOf(20, cur.grams - 20))
                }
            }
            reloadState()
        }

    }

    fun onAddToCart() {
        val id = productId ?: return
        viewModelScope.launch {
            val product = getProductByIdUseCase.getById(id) ?: return@launch

            val preview = selectedAmountPreview ?: defaultPreviewAmount(product.amountType)

            // если товара ещё не было - просто add
            val inCart = getCartUseCase.getCart().items.any { it.productId == id }
            if (!inCart) {
                addProductToCartUseCase.addProduct(id, preview)
            } else {
                setAmountUseCase.setAmount(id, preview)
            }

            selectedAmountPreview = null

            reloadState()
        }

    }

    private suspend fun reloadState() {
        val id = productId ?: return
        val currentState = _state.value ?: ProductDetailUiState()
        _state.value = currentState.copy(isLoading = true)
        val newState = buildState(id)
        _state.value = newState.copy(isLoading = false)

    }

    private suspend fun buildState(id: String): ProductDetailUiState {
        val product = getProductByIdUseCase.getById(id) ?: error("Product not found")

        val cart = getCartUseCase.getCart()
        val realItem = cart.items.find { it.productId == id }
        val inCart = realItem != null
        val addButtonText = if (inCart) "Added" else "Add to cart"
        val isAddEnabled = !inCart

        val isFavourite = isFavouriteUseCase.isFavourite(id)

        val imageRes = imageKeyResolver.resolve(product.imageKey)

        val amountToShow = realItem?.amount
            ?: selectedAmountPreview
            ?: defaultPreviewAmount(product.amountType)

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

    private fun defaultPreviewAmount(type: AmountType): Amount =
        when (type) {
            AmountType.PIECE -> Piece(1)
            AmountType.WEIGHT -> Grams(1000)
        }


}

