package com.example.myshop.testutil

import com.example.myshop.domain.cart.CartRepository
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.cart.model.Cart
import com.example.myshop.domain.cart.model.CartItem
import com.example.myshop.domain.common.Money
import com.example.myshop.domain.favourite.FavouriteRepository
import com.example.myshop.domain.favourite.model.Favourite
import com.example.myshop.domain.favourite.model.FavouriteItem
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.model.OrderItem
import com.example.myshop.domain.order.model.OrderStatus
import com.example.myshop.domain.order.model.randomOrderStatus
import com.example.myshop.domain.order.repository.OrderRepository
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.domain.product.model.Brand
import com.example.myshop.domain.product.model.Category
import com.example.myshop.domain.product.model.Currency
import com.example.myshop.domain.product.model.PricingUnit
import com.example.myshop.domain.product.model.Product
import com.example.myshop.domain.product.model.ProductTag
import com.example.myshop.domain.product.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }

}

class FakeProductRepository(
    products: List<Product>,
    private val loadError: Exception? = null
) : ProductRepository {
    private val productsById = products.associateBy { it.id }

    override suspend fun getAllProducts(): List<Product> {
        loadError?.let { throw it }
        return productsById.values.toList()
    }

    override suspend fun getById(id: String): Product? = productsById[id]

    override suspend fun getProductsByCategory(category: Category): List<Product> {
        loadError?.let { throw it }
        return productsById.values.filter { it.category == category }
    }
}

class FakeCartRepository(initialCart: Cart = Cart()) : CartRepository {
    private val cartFlow = MutableStateFlow(initialCart)

    fun emit(cart: Cart) {
        cartFlow.value = cart
    }

    override suspend fun getCart(): Cart = cartFlow.value

    override fun observeCart(): Flow<Cart> = cartFlow

    override suspend fun addToCart(productId: String, amount: Amount) {
        val otherItems = cartFlow.value.items.filterNot { it.productId == productId }
        cartFlow.value = Cart(otherItems + CartItem(productId, amount))
    }

    override suspend fun removeProduct(productId: String) {
        cartFlow.value = Cart(cartFlow.value.items.filterNot { it.productId == productId })
    }

    override suspend fun clearProducts() {
        cartFlow.value = Cart()
    }

    override suspend fun setAmount(productId: String, amount: Amount) {
        cartFlow.value = Cart(
            cartFlow.value.items.map { item ->
                if (item.productId == productId) item.copy(amount = amount) else item
            }
        )
    }
}

class FakeFavouriteRepository(initialFavourite: Favourite = Favourite()) : FavouriteRepository {
    private val favouriteFlow = MutableStateFlow(initialFavourite)

    fun emit(favourite: Favourite) {
        favouriteFlow.value = favourite
    }

    override suspend fun getFavourite(): Favourite = favouriteFlow.value

    override fun observeFavourite(): Flow<Favourite> = favouriteFlow

    override suspend fun addToFavourite(id: String) {
        if (favouriteFlow.value.items.none { it.productId == id }) {
            favouriteFlow.value = Favourite(favouriteFlow.value.items + FavouriteItem(id))
        }
    }

    override suspend fun removeFavouriteItem(id: String) {
        favouriteFlow.value = Favourite(
            favouriteFlow.value.items.filterNot { it.productId == id }
        )
    }

    override suspend fun clearFavourite() {
        favouriteFlow.value = Favourite()
    }

    override suspend fun isFavourite(id: String): Boolean {
        return favouriteFlow.value.items.any { it.productId == id }
    }

    override suspend fun toggle(productId: String): Boolean {
        val isFavourite = isFavourite(productId)
        if (isFavourite) removeFavouriteItem(productId) else addToFavourite(productId)
        return !isFavourite
    }
}

class FakeOrderRepository(initialOrders: List<Order> = emptyList()) : OrderRepository {
    private val ordersFlow = MutableStateFlow(initialOrders)

    val savedOrders: List<Order>
        get() = ordersFlow.value

    override suspend fun saveOrder(order: Order) {
        ordersFlow.value += order
    }

    override fun observeOrders(): Flow<List<Order>> = ordersFlow

    override suspend fun getOrderById(orderId: String): Order? {
        return ordersFlow.value.firstOrNull { order -> order.id == orderId }
    }
}

fun testOrder(id: String = "123"): Order {
    return Order(
        id = id,
        createdAtMillis = 0,
        status = OrderStatus.PROCESSING,
        items = listOf(
            OrderItem(
                productId = "apple",
                title = "Apple",
                subtitle = "1 unit",
                imageKey = "",
                amount = Amount.Piece(1),
                lineTotal = Money(100, Currency.USD)
            )
        ),
        total = Money(100, Currency.USD)
    )
}

fun testProduct(
    id: String = "apple",
    title: String = "Apple",
    priceCents: Long = 100,
    amountType: AmountType = AmountType.PIECE,
    pricingUnit: PricingUnit = PricingUnit.PER_ITEM,
    category: Category = Category.FRUITS_VEGETABLES,
    brand: Brand = Brand.FRESHFIELD,
    tags: Set<ProductTag> = emptySet()
): Product {
    return Product(
        id = id,
        title = title,
        subtitle = "1 unit",
        description = "Test product",
        imageKey = "",
        price = Money(priceCents, Currency.USD),
        amountType = amountType,
        pricingUnit = pricingUnit,
        tags = tags,
        category = category,
        brand = brand
    )
}

fun cartWith(productId: String = "apple", amount: Amount = Amount.Piece(1)): Cart {
    return Cart(listOf(CartItem(productId, amount)))
}

fun favouriteWith(productId: String = "apple"): Favourite {
    return Favourite(listOf(FavouriteItem(productId)))
}
