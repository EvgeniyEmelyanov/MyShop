package com.example.myshop.features.explore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.PriceSort
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase,
) : ViewModel() {

    private val provider = ExploreCategoriesProvider

    private var allProducts: List<Product> = emptyList()

    private val _state = MutableLiveData(ExploreUiState())
    val state: LiveData<ExploreUiState> = _state

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

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
        val currentState = _state.value ?: ExploreUiState()
        _state.value = currentState.copy(isLoading = true)
        val newState = buildState()
        _state.value = newState.copy(isLoading = false)
    }

    private suspend fun buildState(): ExploreUiState {

        val categories = provider.getCategories()

        allProducts = getAllProductsUseCase.getAllProducts()


        return ExploreUiState(
            categories = categories, products = emptyList()
        )

    }

    fun onFilterChanged(filterParams: FilterParams) {
        val currentState = _state.value ?: ExploreUiState()
        val visibleProducts = getVisibleProducts(currentState.searchQuery, filterParams)

        _state.value = currentState.copy(
            filterParams = filterParams, products = visibleProducts
        )
    }

    fun onSearchQueryChanged(query: String) {
        val currentState = _state.value ?: ExploreUiState()
        val visibleProducts = getVisibleProducts(
            query,
            currentState.filterParams
        )

        _state.value = currentState.copy(
            searchQuery = query, products = visibleProducts
        )
    }

    private fun getVisibleProducts(
        query: String, filterParams: FilterParams
    ): List<CommonProductUiModel> {

        val normalizedQuery = query.trim()

        val filteredProducts = if (normalizedQuery.isBlank()) {
            emptyList()
        } else {
            allProducts.filter { product ->
                product.title.contains(normalizedQuery, ignoreCase = true)
            }
        }

        val filteredByCategories = if (filterParams.categories.isEmpty()) {
            filteredProducts
        } else {
            filteredProducts.filter { product ->
                product.category in filterParams.categories
            }
        }

        val filterByPrice = when (filterParams.priceSort) {
            PriceSort.LOW_TO_HIGH -> filteredByCategories.sortedBy { it.price.cents }
            PriceSort.HIGH_TO_LOW -> filteredByCategories.sortedByDescending { it.price.cents }
            null -> filteredByCategories
        }





        return filterByPrice.map(::toProductUiModel)
    }

    private fun toProductUiModel(product: Product): CommonProductUiModel {
        return CommonProductUiModel(
            id = product.id,
            title = product.title,
            subtitle = product.subtitle,
            imageRes = imageKeyResolver.resolve(product.imageKey),
            priceText = moneyFormatter.format(product.price)
        )

    }

}