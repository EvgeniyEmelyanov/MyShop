package com.example.myshop.features.shop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.cart.usecase.ObserveCartUseCase
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase,
    private val observeCartUseCase: ObserveCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShopUiState())
    val state = _state.asStateFlow()
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()
    private val groceriesCategoriesProvider = GroceriesCategoriesProvider
    private val bannersProvider = BannersProvider

    private var observationJob: Job? = null

    fun load() {
        observationJob?.cancel()
        observationJob = viewModelScope.launch {
            reloadState()
        }
    }

    fun onAddToCart(productId: String) {
        viewModelScope.launch {
            when (val result = addProductToCartIfAbsentUseCase(productId)) {
                is AddToCartResult.Added -> Unit
                is AddToCartResult.AlreadyInCart -> {
                    _toastMessage.emit("${result.productTitle} is already in cart")
                }

                AddToCartResult.ProductNotFound -> Unit
            }
        }
    }

    private suspend fun reloadState() {
        val currentState = _state.value
        _state.value = currentState.copy(contentState = ContentState.LOADING)

        try {
            val products = getAllProductsUseCase.getAllProducts()

            observeCartUseCase().collect { cart ->
                val cartProductIds = cart.items
                    .map { item -> item.productId }
                    .toSet()

                val newState = buildState(products, cartProductIds)

                val hasProducts = newState.exclusiveOffers.isNotEmpty() ||
                        newState.bestSelling.isNotEmpty() ||
                        newState.groceriesProducts.isNotEmpty()

                _state.value = newState.copy(
                    contentState = ContentState.fromHasContent(hasProducts)
                )
            }
        } catch (error: Exception) {
            if (error is CancellationException) throw error

            _state.value = currentState.copy(contentState = ContentState.ERROR)
        }
    }

    private fun buildState(
        products: List<Product>,
        cartProductIds: Set<String>
    ): ShopUiState {

        val exclusiveOffers = products.filter { it.tags.contains(ProductTag.EXCLUSIVE_OFFER) }
            .map { product ->
                toProductCardUiModel(product, cartProductIds)
            }

        val bestSelling = products.filter { it.tags.contains(ProductTag.BEST_SELLING) }
            .map { product ->
                toProductCardUiModel(product, cartProductIds)
            }

        val groceriesProducts = products.filter { it.tags.contains(ProductTag.GROCERIES_PRODUCT) }
            .map { product ->
                toProductCardUiModel(product, cartProductIds)
            }

        return ShopUiState(
            banners = bannersProvider.getBanners(),
            exclusiveOffers = exclusiveOffers,
            bestSelling = bestSelling,
            groceriesProducts = groceriesProducts,
            groceriesCategories = groceriesCategoriesProvider.getCategories()
        )
    }

    private fun toProductCardUiModel(
        product: Product,
        cartProductIds: Set<String>
    ): CommonProductUiModel {
        return CommonProductUiModel(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            priceText = moneyFormatter.format(product.price),
            imageRes = imageKeyResolver.resolve(product.imageKey),
            inCart = product.id in cartProductIds
        )
    }

}

