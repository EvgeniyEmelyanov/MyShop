package com.example.myshop.domain.product.usecase

import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository

class GetProductsByCategoryUseCase(private val productRepository: ProductRepository) {

    fun getByCategory(category: Category): List<Product> {
        return productRepository.getProductsByCategory(category)

    }
}