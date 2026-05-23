package com.example.myshop.data.product.remote.datasource

import com.example.myshop.data.product.remote.api.ProductApi
import com.example.myshop.data.product.remote.model.ProductDto
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val api: ProductApi
) {

    suspend fun getAllProducts(): List<ProductDto> {
        return api.getProducts()
    }
}
