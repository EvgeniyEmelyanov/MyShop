package com.example.myshop.features.productsByCategory.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.domain.product.usecase.GetProductsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProductsByCategoryViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver
    ) : ViewModel() {

    private var currentCategory: Category? = null

    private val _state = MutableLiveData(ProductsByCategoryUiState())
    val state: LiveData<ProductsByCategoryUiState> = _state
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun setCategory(category: Category) {
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
                _toastMessage.value = "${product.title} already in cart"
            }
        }
    }

    fun toastShown() {
        _toastMessage.value = null
    }

    private suspend fun reloadState() {
        val currentState = _state.value ?: ProductsByCategoryUiState()
        _state.value = currentState.copy(isLoading = true)
        val newState = buildState()
        _state.value = newState.copy(isLoading = false)
    }

    private suspend fun buildState(): ProductsByCategoryUiState {
        val category = currentCategory ?: return ProductsByCategoryUiState()

        val products = getProductsByCategoryUseCase.getByCategory(category)

        val uiProducts = products.map { product ->
            CommonProductUiModel(
                id = product.id,
                title = product.title,
                subtitle = product.subtitle,
                priceText = moneyFormatter.format(product.price),
                imageRes = imageKeyResolver.resolve(product.imageKey)
            )
        }

        return ProductsByCategoryUiState(
            products = uiProducts
        )
    }

    private fun startAmount(type: AmountType): Amount =
        when (type) {
            AmountType.PIECE -> Amount.Piece(1)
            AmountType.WEIGHT -> Amount.Grams(1000)

        }
}
