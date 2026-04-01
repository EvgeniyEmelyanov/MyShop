package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetAllProductsUseCase(private val productRepository: ProductRepository) {

    suspend fun getAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            delay(500)
            productRepository.getAllProducts()
        }
    }

}