package com.example.myshop.data.product.repository

import com.example.myshop.data.product.datasource.ProductStore
import com.example.myshop.data.product.mapper.ProductPricingMapper
import com.example.myshop.data.product.model.ProductUnit
import com.example.myshop.data.product.remote.datasource.ProductRemoteDataSource
import com.example.myshop.data.product.remote.mapper.toDomain
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.repository.ProductRepository
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProductsByCategory(category: Category): List<Product> {
        return runCatching {
            remoteDataSource.getAllProducts().map { dto -> dto.toDomain() }
                .filter { product -> product.category == category }
        }.getOrElse {
            localProducts().filter { product -> product.category == category }
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return runCatching {
            remoteDataSource.getAllProducts().map { dto ->
                dto.toDomain()
            }
        }.getOrElse {
            localProducts()
        }

    }

    override suspend fun getById(id: String): Product? {
        return runCatching {
            remoteDataSource.getAllProducts().map { dto -> dto.toDomain() }
                .firstOrNull { product -> product.id == id }
        }.getOrElse {
            ProductStore.findById(id)?.toDomain()
        }
    }

    private fun localProducts(): List<Product> {
        return ProductStore.allProducts.map { rawProduct ->
            rawProduct.toDomain()
        }
    }

    private fun com.example.myshop.data.product.model.Product.toDomain(): Product {
        return Product(
            id = id,
            title = title,
            subtitle = weight,
            description = productDescription,
            imageKey = imageKey,
            price = parseMoney(price),
            amountType = unit.toAmountType(),
            pricingUnit = ProductPricingMapper.fromWeight(weight),
            tags = tags,
            category = category
        )
    }

    private fun ProductUnit.toAmountType(): AmountType = when (this) {
        ProductUnit.PIECE -> AmountType.PIECE
        ProductUnit.GRAM -> AmountType.WEIGHT
    }

    private fun parseMoney(raw: String): Money {
        val normalized = raw.trim().replace(Regex("[^0-9.]"), "")
        val cents =
            BigDecimal(normalized).multiply(BigDecimal(100)).setScale(0, RoundingMode.HALF_UP)
                .toLong()

        return Money(cents = cents, currency = Currency.USD)
    }
}
