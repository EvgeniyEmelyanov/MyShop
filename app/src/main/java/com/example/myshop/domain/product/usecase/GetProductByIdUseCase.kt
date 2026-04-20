package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productRepository: ProductRepository) {

    suspend operator fun invoke(id: String): Product? {
        return productRepository.getById(id)
    }
}


