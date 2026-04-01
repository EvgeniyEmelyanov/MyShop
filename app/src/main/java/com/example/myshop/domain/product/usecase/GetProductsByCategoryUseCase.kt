package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetProductsByCategoryUseCase(private val productRepository: ProductRepository) {

   suspend fun getByCategory(category: Category): List<Product> {
        return withContext(Dispatchers.IO) {
            delay(500)
            productRepository.getProductsByCategory(category)
        }
    }
}