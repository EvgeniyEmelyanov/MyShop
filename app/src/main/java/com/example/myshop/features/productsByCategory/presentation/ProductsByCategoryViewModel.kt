package com.example.myshop.features.productsByCategory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.ProductFilter
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.domain.product.usecase.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsByCategoryViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,
    private val productFilter: ProductFilter
) : ViewModel() {

    private var currentCategory: Category? = null

    private val _state = MutableStateFlow(ProductsByCategoryUiState())
    val state = _state.asStateFlow()
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun setCategory(category: Category) {
        if (currentCategory == category) {
            return
        }

        currentCategory = category
        load()
    }

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun onAddProduct(productId: String) {
        viewModelScope.launch {
            val cart = getCartUseCase()

            val cartItem = cart.items.find { it.productId == productId }

            val product = getProductByIdUseCase(productId) ?: return@launch

            if (cartItem == null) {
                val amount = startAmount(product.amountType)
                addProductToCartUseCase(productId, amount)
                reloadState()
            } else {
                _toastMessage.emit("${product.title} already in cart")
            }
        }
    }

    fun onFilterChanged(filterParams: FilterParams) {
        viewModelScope.launch {
            val currentState = _state.value
            _state.value = currentState.copy(
                filterParams = filterParams,
                contentState = ContentState.LOADING
            )

            loadProducts(filterParams)
        }
    }

    private suspend fun getVisibleProducts(
        filterParams: FilterParams
    ): List<CommonProductUiModel> {
        val category = currentCategory ?: return emptyList()

        val products = getProductsByCategoryUseCase.getByCategory(category)

        return productFilter.apply(products, filterParams).map(::toProductUiModel)
    }

    private suspend fun reloadState() {
        val stateBeforeLoad = _state.value
        _state.value = stateBeforeLoad.copy(contentState = ContentState.LOADING)

        loadProducts(stateBeforeLoad.filterParams)
    }

    private suspend fun loadProducts(filterParams: FilterParams) {
        try {
            val products = getVisibleProducts(filterParams)
            val latestState = _state.value

            _state.value = latestState.copy(
                products = products,
                filterParams = filterParams,
                contentState = ContentState.fromHasContent(products.isNotEmpty())
            )
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            val latestState = _state.value
            _state.value = latestState.copy(
                filterParams = filterParams,
                contentState = ContentState.ERROR
            )
        }
    }

    private fun startAmount(type: AmountType): Amount = when (type) {
        AmountType.PIECE -> Amount.Piece(1)
        AmountType.WEIGHT -> Amount.Grams(1000)

    }

    private fun toProductUiModel(product: Product): CommonProductUiModel {
        return CommonProductUiModel(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            priceText = moneyFormatter.format(product.price),
            imageRes = imageKeyResolver.resolve(product.imageKey)
        )

    }
}
