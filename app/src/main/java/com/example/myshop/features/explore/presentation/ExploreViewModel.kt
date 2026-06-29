package com.example.myshop.features.explore.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.ProductFilter
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.AddToCartResult
import com.example.myshop.domain.cart.usecase.AddProductToCartIfAbsentUseCase
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val imageKeyResolver: ImageKeyResolver,
    private val moneyFormatter: MoneyFormatter,
    private val addProductToCartIfAbsentUseCase: AddProductToCartIfAbsentUseCase,
    private val productFilter: ProductFilter,
) : ViewModel() {

    private val provider = ExploreCategoriesProvider

    private var allProducts: List<Product> = emptyList()

    private val _state = MutableStateFlow(ExploreUiState())
    val state = _state.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private var searchJob: Job? = null

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
                    _toastMessage.emit("${result.productTitle} is already in cart")
                }

                AddToCartResult.ProductNotFound -> Unit
            }
        }
    }

    private suspend fun reloadState() {
        val stateBeforeLoad = _state.value
        _state.value = stateBeforeLoad.copy(contentState = ContentState.LOADING)

        try {
            val categories = provider.getCategories()
            allProducts = getAllProductsUseCase.getAllProducts()
            val latestState = _state.value
            val products = getVisibleProducts(latestState.searchQuery, latestState.filterParams)

            _state.value = latestState.copy(
                categories = categories,
                products = products,
                contentState = contentState(latestState.searchQuery, products)
            )
        } catch (error: Exception) {
            if (error is CancellationException) throw error
            val latestState = _state.value
            _state.value = latestState.copy(contentState = ContentState.ERROR)
        }
    }

    fun onFilterChanged(filterParams: FilterParams) {
        val currentState = _state.value

        val visibleProducts = getVisibleProducts(currentState.searchQuery, filterParams)

        _state.value = currentState.copy(
            filterParams = filterParams,
            products = visibleProducts,
            contentState = contentState(currentState.searchQuery, visibleProducts)
        )
    }

    fun onSearchQueryChanged(query: String) {
        val currentState = _state.value

        _state.value =
            currentState.copy(searchQuery = query, filterParams = currentState.filterParams)

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(300)

            val latestState = _state.value
            val visibleProducts = getVisibleProducts(query, latestState.filterParams)

            _state.value = latestState.copy(
                products = visibleProducts,
                filterParams = latestState.filterParams,
                contentState = contentState(query, visibleProducts)
            )
        }
    }

    private fun contentState(
        query: String,
        products: List<CommonProductUiModel>
    ): ContentState {
        return if (query.isBlank()) {
            ContentState.CONTENT
        } else {
            ContentState.fromHasContent(products.isNotEmpty())
        }
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

        return productFilter.apply(filteredProducts, filterParams).map(::toProductUiModel)
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
