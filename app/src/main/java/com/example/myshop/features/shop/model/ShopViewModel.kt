package com.example.myshop.features.shop.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.R
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase

class ShopViewModel(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addProductUseCase: AddProductToCartUseCase,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver,
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {

    private val _state = MutableLiveData(ShopUiState())
    val state: LiveData<ShopUiState> = _state

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    fun load() {
        _state.value = buildState()
    }

    fun onAddProduct(productId: String) {
        val cart = getCartUseCase.getCart()
        val cartItem = cart.items.find { it.productId == productId }

        if (cartItem == null) {
            val product = getAllProductsUseCase.getAllProducts()
                .find { it.id == productId }
                ?: return

            val amount = startAmount(product.amountType)
            addProductUseCase.addProduct(productId, amount)
        } else {
            _toastMessage.value = "Product already in cart"
        }
        load()
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

        val banners = listOf(
            BannerUiModel("Fresh Vegetables", "Get Up To 40% OFF"),
            BannerUiModel("Hot Deals", "Only Today"),
            BannerUiModel("Mega Sale", "Up to 70% OFF")
        )

        val groceriesCategories = listOf(
            GroceriesCategoryUiModel(
                title = "Pulses",
                imageRes = R.drawable.pulses_picture,
                backgroundColorRes = R.color.bg_grocery_pulses
            ),
            GroceriesCategoryUiModel(
                title = "Rice",
                imageRes = R.drawable.rice_pictute,
                backgroundColorRes = R.color.bg_grocery_rice
            ),
            GroceriesCategoryUiModel(
                title = "Meat",
                imageRes = R.drawable.rice_pictute,
                backgroundColorRes = R.color.bg_grocery_meat
            )
        )

        return ShopUiState(
            banners = banners,
            exclusiveOffers = exclusiveOffers,
            bestSelling = bestSelling,
            groceriesProducts = groceriesProducts,
            groceriesCategories = groceriesCategories
        )
    }

    private fun toProductCardUiModel(product: com.example.myshop.domain.product.model.Product): ProductCardUiModel {
        return ProductCardUiModel(
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
