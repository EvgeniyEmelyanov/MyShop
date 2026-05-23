package com.example.myshop.data.product.remote.api

import com.example.myshop.data.product.remote.model.ProductDto
import retrofit2.http.GET

interface ProductApi {

    @GET("products.json")
    suspend fun getProducts(): List<ProductDto>
}