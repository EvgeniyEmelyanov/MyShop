package com.example.myshop.di

import com.example.myshop.data.cart.repository.CartRepositoryImpl
import com.example.myshop.data.product.repository.ProductRepositoryImpl
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.AddProductUseCase
import com.example.myshop.domain.cart.usecase.CalculateCartTotalsUseCase
import com.example.myshop.domain.cart.usecase.ClearProductsUseCase
import com.example.myshop.domain.cart.usecase.DecreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.GetCartUseCase
import com.example.myshop.domain.cart.usecase.IncreaseAmountUseCase
import com.example.myshop.domain.cart.usecase.RemoveProductUseCase
import com.example.myshop.domain.cart.usecase.SetAmountUseCase
import com.example.myshop.domain.product.repository.ProductRepository
import com.example.myshop.domain.product.usecase.GetProductByIdUseCase
import com.example.myshop.core.ui.formatter.MoneyFormatter
import com.example.myshop.core.ui.formatter.QuantityFormatter
import com.example.myshop.core.ui.image.ImageKeyResolver
import com.example.myshop.data.favourite.FavouriteRepositoryImpl
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.usecase.AddToFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ClearFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.GetFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.IsFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.RemoveFromFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase

object AppGraph {

    //Product
    val productRepository: ProductRepository = ProductRepositoryImpl()
    val getProductByIdUseCase = GetProductByIdUseCase(productRepository)

    //Formatters
    val imageKeyResolver = ImageKeyResolver
    val moneyFormatter = MoneyFormatter()
    val quantityFormatter = QuantityFormatter()
    val linePriceCalculator = LinePriceCalculator()

    //Cart
    val cartRepository: CartRepository = CartRepositoryImpl()
    val getCartUseCase = GetCartUseCase(cartRepository)
    val addProductUseCase = AddProductUseCase(cartRepository)
    val setAmountUseCase = SetAmountUseCase(cartRepository)
    val removeProductUseCase = RemoveProductUseCase(cartRepository)
    val clearProductsUseCase = ClearProductsUseCase(cartRepository)
    val increaseAmountUseCase = IncreaseAmountUseCase(cartRepository)
    val decreaseAmountUseCase = DecreaseAmountUseCase(cartRepository)
    val calculateCartTotalsUseCase =
        CalculateCartTotalsUseCase(cartRepository, productRepository, linePriceCalculator)

    //Favourite
    val favouriteRepository: FavouriteRepository = FavouriteRepositoryImpl()
    val getFavouriteUseCase = GetFavouriteUseCase(favouriteRepository)
    val addToFavouriteUseCase = AddToFavouriteUseCase(favouriteRepository)
    val removeFromFavouriteUseCase = RemoveFromFavouriteUseCase(favouriteRepository)
    val clearFavouriteUseCase = ClearFavouriteUseCase(favouriteRepository)
    val isFavouriteUseCase = IsFavouriteUseCase(favouriteRepository)
    val toggleFavouriteUseCase = ToggleFavouriteUseCase(favouriteRepository)


}