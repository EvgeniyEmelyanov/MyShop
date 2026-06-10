package com.example.myshop.features.shop.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ShopUiState())
    val state: LiveData<ShopUiState> = _state

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage
    private val groceriesCategoriesProvider = GroceriesCategoriesProvider
    private val bannersProvider = BannersProvider

    fun load() {
        viewModelScope.launch {
            reloadState()
        }
    }

    fun onAddToCart(productId: String) {
        viewModelScope.launch {
            when (val result = addProductToCartIfAbsentUseCase(productId)) {
                is AddToCartResult.Added -> reloadState()
                is AddToCartResult.AlreadyInCart -> {
                    _toastMessage.value = "${result.productTitle} is already in cart"
                }

                AddToCartResult.ProductNotFound -> Unit
            }
        }
    }

    fun toastShown() {
        _toastMessage.value = null
    }

    private suspend fun reloadState() {
        val currentState = _state.value ?: ShopUiState()
        _state.value = currentState.copy(contentState = ContentState.LOADING)

        try {
            val newState = buildState()
            val hasProducts = newState.exclusiveOffers.isNotEmpty() ||
                newState.bestSelling.isNotEmpty() ||
                newState.groceriesProducts.isNotEmpty()

            _state.value = newState.copy(
                contentState = ContentState.fromHasContent(hasProducts)
            )
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            _state.value = currentState.copy(contentState = ContentState.ERROR)
        }
    }

    private suspend fun buildState(): ShopUiState {
        val products = getAllProductsUseCase.getAllProducts()

        val exclusiveOffers = products.filter { it.tags.contains(ProductTag.EXCLUSIVE_OFFER) }
            .map(::toProductCardUiModel)

        val bestSelling = products.filter { it.tags.contains(ProductTag.BEST_SELLING) }
            .map(::toProductCardUiModel)

        val groceriesProducts = products.filter { it.tags.contains(ProductTag.GROCERIES_PRODUCT) }
            .map(::toProductCardUiModel)

        return ShopUiState(
            banners = bannersProvider.getBanners(),
            exclusiveOffers = exclusiveOffers,
            bestSelling = bestSelling,
            groceriesProducts = groceriesProducts,
            groceriesCategories = groceriesCategoriesProvider.getCategories()
        )
    }

    private fun toProductCardUiModel(product: Product): CommonProductUiModel {
        return CommonProductUiModel(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            priceText = moneyFormatter.format(product.price),
            imageRes = imageKeyResolver.resolve(product.imageKey)
        )
    }

}

