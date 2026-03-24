package com.example.myshop.features.productsByCategory.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.domain.product.usecase.GetProductsByCategoryUseCase

class ProductsByCategoryViewModel(
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,

    ) : ViewModel() {

    private var currentCategory: Category? = null

    private val _state = MutableLiveData(ProductsByCategoryUiState())
    val state: LiveData<ProductsByCategoryUiState> = _state

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun setCategory(category: Category) {
        currentCategory = category
        refresh()
    }

    private fun refresh() {
        _state.value = buildState()
    }

    fun onAddProduct(productId: String) {
        val cart = getCartUseCase.getCart()
        val cartItem = cart.items.find { it.productId == productId }

        val product = getProductByIdUseCase.getById(productId) ?: return

        if (cartItem == null) {
            val amount = startAmount(product.amountType)
            addProductToCartUseCase.addProduct(productId, amount)
            refresh()
        } else {
            _toastMessage.value = "${product.title} already in cart"
        }

    }

    fun toastShown() {
        _toastMessage.value = null
    }

    private fun buildState(): ProductsByCategoryUiState {
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