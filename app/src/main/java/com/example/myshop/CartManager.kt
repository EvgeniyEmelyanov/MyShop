package com.example.myshop

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.LinkedHashMap

class CartManager(
    private val gramStep: Int = 20,
    private val defaultStartGrams: Int = 1000
) {

    private val itemsMap: MutableMap<String, CartItem> = LinkedHashMap()

    fun getItem(productId: String): CartItem? {
        return itemsMap[productId]
    }

    fun getItems(): List<CartItem> = itemsMap.values.toList()

    fun clear() = itemsMap.clear()

    fun removeItem(productId: String) {
        itemsMap.remove(productId)
    }

    fun addToCart(productId: String) {
        val product = ProductStore.findById(productId) ?: return


        val existing = itemsMap[productId]
        if (existing == null) {
            val startAmount = when (product.unit) {
                ProductUnit.PIECE -> Amount.Pieces(1)
                ProductUnit.GRAM -> Amount.Grams(normalizeGrams(defaultStartGrams))

            }
            itemsMap[productId] = CartItem(productId, startAmount)
            return
        }
        itemsMap[productId] = existing.copy(amount = increaseAmount(existing.amount))
    }

    fun increase(productId: String) {
        val existing = itemsMap[productId] ?: return
        itemsMap[productId] = existing.copy(amount = increaseAmount(existing.amount))
    }

    fun decrease(productId: String) {
        val existing = itemsMap[productId] ?: return

        val newAmount = decreaseAmount(existing.amount)
        if (newAmount == null) {
            // стало 0 → удаляем позицию
            itemsMap.remove(productId)
        } else {
            itemsMap[productId] = existing.copy(amount = newAmount)
        }
    }

    fun lineTotalCents(item: CartItem): Int {
        val product = ProductStore.findById(item.productId) ?: return 0
        val unitPriceCents = parseMoneyToCent(product.price)

        return when (val a = item.amount) {
            is Amount.Pieces -> unitPriceCents * a.count
            is Amount.Grams -> {
                // unitPriceCents = цена за 1 кг → пересчёт на grams
                // cents = pricePerKg * grams / 1000
                ((unitPriceCents.toLong() * a.grams.toLong()) / 1000L).toInt()
            }
        }
    }


    fun setAmount(productId: String, value: Int) {
        val product = ProductStore.findById(productId) ?: return
        if (value <= 0) {
            itemsMap.remove(productId)
            return
        }

        val amount = when (product.unit) {
            ProductUnit.PIECE -> Amount.Pieces(value)
            ProductUnit.GRAM -> Amount.Grams(normalizeGrams(value))
        }
        itemsMap[productId] = CartItem(productId, amount)
    }

    fun cartTotalCents(): Int {
        var sum = 0
        for (item in itemsMap.values) sum += lineTotalCents(item)
        return sum
    }


    fun formatCents(cents: Int): String {
        val dollars = BigDecimal(cents).divide(BigDecimal(100), 2, RoundingMode.HALF_UP)
        return "$$dollars"
    }

    private fun increaseAmount(amount: Amount): Amount {
        return when (amount) {
            is Amount.Pieces -> Amount.Pieces(amount.count + 1)
            is Amount.Grams -> Amount.Grams(amount.grams + gramStep)

        }
    }

    private fun decreaseAmount(amount: Amount): Amount? {
        return when (amount) {
            is Amount.Pieces -> {
                val newCount = amount.count - 1
                if (newCount <= 0) null else Amount.Pieces(newCount)
            }

            is Amount.Grams -> {
                val newGrams = amount.grams - gramStep
                if (newGrams <= 0) null else Amount.Grams(newGrams)
            }
        }
    }

    private fun normalizeGrams(grams: Int): Int {
        val g = if (grams < gramStep) gramStep else grams
        val rem = g % gramStep
        return if (rem == 0) g else (g + (gramStep - rem))
    }

    private fun parseMoneyToCent(raw: String): Int {
        val cleaned = raw.trim()
            .replace(Regex("[^0-9.,]"), "")
            .replace(",", ".")

        if (cleaned.isBlank()) return 0

        val bd = try {
            BigDecimal(cleaned)
        } catch (_: Exception) {
            return 0
        }

        return bd.multiply(BigDecimal(100))
            .setScale(0, RoundingMode.HALF_UP)
            .toInt()

    }

}