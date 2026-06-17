package com.example.myshop.domain.product.repository

import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Product

interface ProductRepository {


    suspend fun getAllProducts(): List<Product>

    suspend fun getById(id: String): Product?

    suspend fun getProductsByCategory(category: Category): List<Product>


}