package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class GetAllProductsUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend fun getAllProducts(): List<Product> {
        return productRepository.getAllProducts()

    }

}
