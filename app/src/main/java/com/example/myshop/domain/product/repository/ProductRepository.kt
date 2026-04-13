package com.example.myshop.domain.product.repository

import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Product

interface ProductRepository {


    fun getAllProducts(): List<Product>

    fun getById (id: String): Product?

    fun getProductsByCategory(category: Category): List<Product>


}