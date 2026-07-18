package com.example.myshop.di

import android.content.Context
import androidx.room.Room
import com.example.myshop.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "myshop_db"
        ).fallbackToDestructiveMigration().build()

    }

    @Provides
    fun provideCartDao(db: AppDatabase) = db.cartDao()

    @Provides
    fun provideFavouriteDao(db: AppDatabase) = db.favouriteDao()

    @Provides
    fun provideOrderDao(db: AppDatabase) = db.orderDao()

    @Provides
    fun provideOrderItemDao(db: AppDatabase) = db.orderItemDao()

}
