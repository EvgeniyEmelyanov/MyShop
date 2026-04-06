package com.example.myshop.di

import android.content.Context
import androidx.room.Room
import com.example.myshop.data.cart.repository.CartRepositoryImpl
import com.example.myshop.data.product.repository.ProductRepositoryImpl
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.service.LinePriceCalculator
import com.example.myshop.domain.cart.usecase.AddProductToCartUseCase
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
import com.example.myshop.data.favourite.repository.FavouriteRepositoryImpl
import com.example.myshop.data.local.AppDatabase
import com.example.myshop.domain.cart.service.DefaultCartAmountFactory
import com.example.myshop.domain.cart.usecase.AddAllFavouriteToCartUseCase
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.usecase.AddToFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ClearFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.GetFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.IsFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.RemoveFromFavouriteUseCase
import com.example.myshop.domain.favourite.usecase.ToggleFavouriteUseCase
import com.example.myshop.domain.product.usecase.GetAllProductsUseCase
import com.example.myshop.domain.product.usecase.GetProductsByCategoryUseCase

object AppGraph {

    private var _db: AppDatabase? = null
    val db: AppDatabase
        get() = _db ?: throw IllegalStateException("Database not initialized")

    fun init(context: Context) {
        if (_db == null) {
            _db = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "myshop_db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    val cartDao by lazy { db.cartDao() }
    val favouriteDao by lazy { db.favouriteDao() }


    // Product
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl() }
    val getProductByIdUseCase by lazy { GetProductByIdUseCase(productRepository) }
    val getAllProductsUseCase by lazy { GetAllProductsUseCase(productRepository) }
    val getProductsByCategoryUseCase by lazy { GetProductsByCategoryUseCase(productRepository) }

    // Formatters
    val imageKeyResolver = ImageKeyResolver
    val moneyFormatter by lazy { MoneyFormatter() }
    val quantityFormatter by lazy { QuantityFormatter() }
    val linePriceCalculator by lazy { LinePriceCalculator() }
    val defaultCartAmountFactory by lazy { DefaultCartAmountFactory() }


    // Cart
    val cartRepository: CartRepository by lazy { CartRepositoryImpl(cartDao) }
    val getCartUseCase by lazy { GetCartUseCase(cartRepository) }
    val addProductToCartUseCase by lazy { AddProductToCartUseCase(cartRepository) }
    val setAmountUseCase by lazy { SetAmountUseCase(cartRepository) }
    val removeProductUseCase by lazy { RemoveProductUseCase(cartRepository) }
    val clearProductsUseCase by lazy { ClearProductsUseCase(cartRepository) }
    val increaseAmountUseCase by lazy { IncreaseAmountUseCase(cartRepository) }
    val decreaseAmountUseCase by lazy { DecreaseAmountUseCase(cartRepository) }
    val calculateCartTotalsUseCase by lazy {
        CalculateCartTotalsUseCase(cartRepository, productRepository, linePriceCalculator)
    }

    val addAllFavouriteToCartUseCase by lazy {
        AddAllFavouriteToCartUseCase(
            cartRepository, favouriteRepository, productRepository, defaultCartAmountFactory
        )
    }

    // Favourite
    val favouriteRepository: FavouriteRepository by lazy { FavouriteRepositoryImpl(favouriteDao) }
    val getFavouriteUseCase by lazy { GetFavouriteUseCase(favouriteRepository) }
    val addToFavouriteUseCase by lazy { AddToFavouriteUseCase(favouriteRepository) }
    val removeFromFavouriteUseCase by lazy { RemoveFromFavouriteUseCase(favouriteRepository) }
    val clearFavouriteUseCase by lazy { ClearFavouriteUseCase(favouriteRepository) }
    val isFavouriteUseCase by lazy { IsFavouriteUseCase(favouriteRepository) }
    val toggleFavouriteUseCase by lazy { ToggleFavouriteUseCase(favouriteRepository) }
}


