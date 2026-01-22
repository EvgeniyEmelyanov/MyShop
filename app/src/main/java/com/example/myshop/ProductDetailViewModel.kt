package com.example.myshop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductDetailViewModel : ViewModel() {

    private var productId: String? = null
    private var selectedAmountPreview: Amount? = null
    private var isDescriptionExpanded: Boolean = false
    private val _state = MutableLiveData<ProductDetailUiState>()
    val state: LiveData<ProductDetailUiState> = _state

    fun load() {
        if (productId == null) return
        _state.value = buildState()
    }


    //Get ProductId from ProductDetailFragment
    fun setProductId(productId: String) {
        if (this.productId != null && this.productId != productId) return
        this.productId = productId
        load()
    }


    fun onPlus() {
        val id = productId ?: return
        val product = ProductStore.findById(id) ?: return

        if (AppState.cartManager.getItem(id) != null) {
            AppState.cartManager.increase(id)
        } else {

            val cur = selectedAmountPreview ?: when (product.unit) {
                ProductUnit.PIECE -> Amount.Pieces(1)
                ProductUnit.GRAM -> Amount.Grams(1000)
            }

            selectedAmountPreview = when (cur) {
                is Amount.Pieces -> Amount.Pieces(cur.count + 1)
                is Amount.Grams -> Amount.Grams(cur.grams + 20) // шаг как в CartManager
            }
        }
        load()
    }

    fun onMinus() {
        val id = productId ?: return
        val product = ProductStore.findById(id) ?: return

        if (AppState.cartManager.getItem(id) != null) {
            AppState.cartManager.decrease(id)
        } else {
            val cur = selectedAmountPreview ?: when (product.unit) {
                ProductUnit.PIECE -> Amount.Pieces(1)
                ProductUnit.GRAM -> Amount.Grams(1000)
            }

            selectedAmountPreview = when (cur) {
                is Amount.Pieces -> Amount.Pieces(maxOf(1, cur.count - 1))
                is Amount.Grams -> Amount.Grams(maxOf(20, cur.grams - 20))
            }
        }
        load()
    }

    fun onAddToCart() {
        val id = productId ?: return
        val product = ProductStore.findById(id) ?: return

        val a = selectedAmountPreview ?: when (product.unit) {
            ProductUnit.PIECE -> Amount.Pieces(1)
            ProductUnit.GRAM -> Amount.Grams(1000)

        }

        when (a) {
            is Amount.Pieces -> AppState.cartManager.setAmount(id, a.count)
            is Amount.Grams -> AppState.cartManager.setAmount(id, a.grams)
        }

        selectedAmountPreview = null
        load()
    }

    fun onToggleDescription() {
        isDescriptionExpanded = !isDescriptionExpanded
        load()
    }

    fun onAddToFavorite() {
        val id = productId ?: return
        AppState.favouriteManager.toggle(id)
        load()
    }

    fun buildState(): ProductDetailUiState {
        val id = productId ?: error("ProductId not set")
        val product = ProductStore.findById(id) ?: error("Product not found")

        val realItem = AppState.cartManager.getItem(id)
        val inCart = realItem != null

        val amountToShow: Amount =
            realItem?.amount
                ?: selectedAmountPreview
                ?: when (product.unit) {
                    ProductUnit.PIECE -> Amount.Pieces(1)
                    ProductUnit.GRAM -> Amount.Grams(1000)
                }

        val countText = when (amountToShow) {
            is Amount.Grams -> "${amountToShow.grams} g"
            is Amount.Pieces -> amountToShow.count.toString()
        }

        val itemForPrice = realItem ?: CartItem(productId = id, amount = amountToShow)
        val cents = AppState.cartManager.lineTotalCents(itemForPrice)
        val priceText = AppState.cartManager.formatCents(cents)

        val addButtonText = if (inCart) "Added" else "Add to cart"
        val isAddEnabled = !inCart
        val isFavorite = AppState.favouriteManager.isFavorite(id)

        return ProductDetailUiState(
            title = product.title,
            weight = product.weight,
            description = product.productDescription,
            imageRes = product.imageRes,
            countText = countText,
            priceText = priceText,
            isInCart = inCart,
            addButtonText = addButtonText,
            isAddEnabled = isAddEnabled,
            isFavorite = isFavorite,
            isDescriptionExpanded = isDescriptionExpanded
        )

    }
}