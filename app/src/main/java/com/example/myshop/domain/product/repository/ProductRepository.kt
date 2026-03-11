package com.example.myshop.domain.product.repository

import com.example.myshop.domain.product.model.Product

interface ProductRepository {

    fun getById (id: String): Product?
}