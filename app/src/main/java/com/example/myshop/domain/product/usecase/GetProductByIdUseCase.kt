package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetProductByIdUseCase(private val productRepository: ProductRepository) {

    suspend fun getById(id: String): Product? {
        return productRepository.getById(id)
    }
}

