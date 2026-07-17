package com.example.myshop.features.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.formatter.DateFormatter
import com.example.myshop.core.formatter.MoneyFormatter
import com.example.myshop.core.image.ImageKeyResolver
import com.example.myshop.core.ui.ContentState
import com.example.myshop.domain.order.model.Order
import com.example.myshop.domain.order.model.OrderStatus
import com.example.myshop.domain.order.usecase.ObserveOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val observeOrdersUseCase: ObserveOrdersUseCase,
    private val dateFormatter: DateFormatter,
    private val moneyFormatter: MoneyFormatter,
    private val imageKeyResolver: ImageKeyResolver
) : ViewModel() {

    private val _state = MutableStateFlow(OrdersUiState())
    val state = _state.asStateFlow()

    private var observeOrdersJob: Job? = null

    init {
        observeOrders()
    }

    fun observeOrders() {
        observeOrdersJob?.cancel()

        observeOrdersJob = viewModelScope.launch {
            observeOrdersUseCase().catch { error ->
                    if (error is CancellationException) throw error
                    _state.value = _state.value.copy(contentState = ContentState.ERROR)
                }.collect { orders ->
                    _state.value = buildState(orders)
                }
        }
    }

    private fun buildState(orders: List<Order>): OrdersUiState {
        val uiOrders = orders.map { order ->
            order.toUiModel()
        }

        val contentState = if (uiOrders.isEmpty()) {
            ContentState.EMPTY
        } else {
            ContentState.CONTENT
        }

        return OrdersUiState(
            orders = uiOrders, contentState = contentState
        )
    }

    private fun Order.toUiModel(): OrderUiModel {
        return OrderUiModel(
            id = id,
            date = dateFormatter.formatDate(createdAtMillis),
            time = dateFormatter.formatTime(createdAtMillis),
            statusFilter = status.toOrdersFilter(),
            status = status.toDisplayText(),
            productImages = items.map { item ->
                imageKeyResolver.resolve(item.imageKey)
            },
            itemsCount = formatItemsCount(items.size),
            total = moneyFormatter.format(total)
        )
    }

    private fun OrderStatus.toOrdersFilter(): OrdersFilter {
        return when (this) {
            OrderStatus.PROCESSING -> OrdersFilter.PROCESSING
            OrderStatus.COMPLETED -> OrdersFilter.COMPLETED
            OrderStatus.CANCELED -> OrdersFilter.CANCELED
        }
    }

    private fun OrderStatus.toDisplayText(): String {
        return when (this) {
            OrderStatus.PROCESSING -> "Processing"
            OrderStatus.COMPLETED -> "Completed"
            OrderStatus.CANCELED -> "Canceled"
        }
    }

    private fun formatItemsCount(count: Int): String {
        return if (count == 1) {
            "$count item"
        } else {
            "$count items"
        }
    }
}
