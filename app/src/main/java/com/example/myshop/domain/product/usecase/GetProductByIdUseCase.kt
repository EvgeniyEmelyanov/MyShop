package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository

class GetProductByIdUseCase(private val productRepository: ProductRepository) {

    fun getById(id: String): Product? {
        return productRepository.getById(id)

    }
}