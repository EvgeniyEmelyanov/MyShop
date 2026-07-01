package com.example.myshop.domain.favourite.usecase

import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ToggleFavouriteUseCaseTest {

    @Test
    fun `toggle returns true when repository returns true`() = runBlocking {
        val fakeRepository = FakeFavouriteRepository(toggleResult = true)
        val useCase = ToggleFavouriteUseCase(favouriteRepository = fakeRepository)

        val result = useCase.toggle("product")

        assertEquals("product", fakeRepository.toggledProductId)
        assertEquals(true, result)
    }

    @Test
    fun `toggle returns false when repository returns false`() = runBlocking {
        val fakeRepository = FakeFavouriteRepository(toggleResult = false)
        val useCase = ToggleFavouriteUseCase(favouriteRepository = fakeRepository)

        val result = useCase.toggle("product")

        assertEquals("product", fakeRepository.toggledProductId)
        assertEquals(false, result)
    }

    private class FakeFavouriteRepository(
        private val toggleResult: Boolean
    ) : FavouriteRepository {

        var toggledProductId: String? = null

        override suspend fun getFavourite(): Favourite = Favourite()

        override fun observeFavourite(): Flow<Favourite> {
            return flowOf(Favourite())
        }

        override suspend fun addToFavourite(id: String) = Unit

        override suspend fun removeFavouriteItem(id: String) = Unit

        override suspend fun clearFavourite() = Unit

        override suspend fun isFavourite(id: String): Boolean = false

        override suspend fun toggle(productId: String): Boolean {
            toggledProductId = productId
            return toggleResult
        }
    }
}
