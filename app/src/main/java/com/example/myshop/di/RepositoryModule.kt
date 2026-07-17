package com.example.myshop.di

import com.example.myshop.data.cart.repository.CartRepositoryImpl
import com.example.myshop.data.favourite.repository.FavouriteRepositoryImpl
import com.example.myshop.data.order.repository.OrderRepositoryImpl
import com.example.myshop.data.product.repository.ProductRepositoryImpl
import com.example.myshop.data.user.repository.UserProfileRepositoryImpl
import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.order.repository.OrderRepository
import com.example.myshop.domain.product.repository.ProductRepository
import com.example.myshop.domain.user.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository


    @Binds
    @Singleton
    abstract fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository
}
