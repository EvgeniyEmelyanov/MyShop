package com.example.myshop.features.shop.model

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
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase

class ShopViewModel(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addProductUseCase: AddProductToCartUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,
    private val getCartUseCase: GetCartUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ShopUiState())
    val state: LiveData<ShopUiState> = _state

    private val groceriesCategoriesProvider = GroceriesCategoriesProvider
    private val bannersProvider = BannersProvider

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun load() {
        _state.value = buildState()
    }

    fun onAddProduct(productId: String) {
        val cart = getCartUseCase.getCart()
        val cartItem = cart.items.find { it.productId == productId }

        val product = getProductByIdUseCase.getById(productId) ?: return

        if (cartItem == null) {


            val amount = startAmount(product.amountType)
            addProductUseCase.addProduct(productId, amount)
            load()
        } else {
            _toastMessage.value = "${product.title} already in cart"
        }

    }

    fun toastShown() {
        _toastMessage.value = null
    }

    private fun buildState(): ShopUiState {
        val products = getAllProductsUseCase.getAllProducts()

        val exclusiveOffers = products
            .filter { it.tags.contains(ProductTag.EXCLUSIVE_OFFER) }
            .map(::toProductCardUiModel)

        val bestSelling = products
            .filter { it.tags.contains(ProductTag.BEST_SELLING) }
            .map(::toProductCardUiModel)

        val groceriesProducts = products
            .filter { it.tags.contains(ProductTag.GROCERIES_PRODUCT) }
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

    private fun startAmount(type: AmountType): Amount =
        when (type) {
            AmountType.PIECE -> Amount.Piece(1)
            AmountType.WEIGHT -> Amount.Grams(1000)
        }
}
