package com.example.myshop.features.productdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.formatter.QuantityFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.di.AppGraph
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.SetAmountUseCase
import com.example.myshop.domain.favourite.usecase.IsFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase

class ProductDetailViewModelFactory(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val setAmountUseCase: SetAmountUseCase,
    private val increaseAmountUseCase: IncreaseAmountUseCase,
    private val decreaseAmountUseCase: DecreaseAmountUseCase,
    private val calculateCartTotalsUseCase: CalculateCartTotalsUseCase,
    private val quantityFormatter: QuantityFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val linePriceCalculator: LinePriceCalculator,
    private val imageKeyResolver: ImageKeyResolver,
    private val isFavouriteUseCase: IsFavouriteUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(
                getProductByIdUseCase,
                getCartUseCase,
                addProductToCartUseCase,
                setAmountUseCase,
                increaseAmountUseCase,
                decreaseAmountUseCase,
                calculateCartTotalsUseCase,
                quantityFormatter,
                moneyFormatter,
                linePriceCalculator,
                imageKeyResolver,
                isFavouriteUseCase,
                toggleFavouriteUseCase
            ) as T
        }
        error("Unknown VM: ${modelClass.name}")
    }
}