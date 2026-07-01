package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import com.example.myshop.domain.favourite.model.FavouriteItem
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveFavouriteUseCaseTest {

    @Test
    fun `observe favourite forwards repository updates`() = runBlocking {
        val initialFavourite = Favourite()
        val updatedFavourite = Favourite(
            items = listOf(FavouriteItem(productId = "apple"))
        )
        val repository = FakeFavouriteRepository(initialFavourite)
        val useCase = ObserveFavouriteUseCase(repository)

        val emissions = async(start = CoroutineStart.UNDISPATCHED) {
            useCase().take(2).toList()
        }

        repository.emit(updatedFavourite)

        assertEquals(listOf(initialFavourite, updatedFavourite), emissions.await())
    }

    private class FakeFavouriteRepository(
        initialFavourite: Favourite
    ) : FavouriteRepository {
        private val favouriteFlow = MutableStateFlow(initialFavourite)

        fun emit(favourite: Favourite) {
            favouriteFlow.value = favourite
        }

        override suspend fun getFavourite(): Favourite = favouriteFlow.value

        override fun observeFavourite(): Flow<Favourite> = favouriteFlow

        override suspend fun addToFavourite(id: String) = Unit

        override suspend fun removeFavouriteItem(id: String) = Unit

        override suspend fun clearFavourite() = Unit

        override suspend fun isFavourite(id: String): Boolean = false

        override suspend fun toggle(productId: String): Boolean = false
    }
}
