package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository

class GetAllProductsUseCase(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> {
        return productRepository.getAllProducts()
    }
}