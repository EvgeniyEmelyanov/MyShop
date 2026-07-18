package com.example.myshop.features.orders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshop.R
import com.example.myshop.core.ui.ContentState
import com.example.myshop.features.orders.presentation.OrderUiModel
import com.example.myshop.features.orders.presentation.OrdersFilter
import com.example.myshop.features.orders.presentation.OrdersUiState

@Composable
fun OrderScreen(
    state: OrdersUiState
) {

    var selectedFilter by remember { mutableStateOf(OrdersFilter.ALL) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Column {
            OrdersStatusFilter(
                selectedFilter = selectedFilter,
                onFilterSelected = { filter ->
                    selectedFilter = filter
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                when (state.contentState) {
                    ContentState.LOADING -> {
                        OrdersMessage(contentState = ContentState.LOADING)
                    }

                    ContentState.CONTENT -> {
                        OrdersList(
                            orders = state.orders,
                            selectedFilter = selectedFilter
                        )
                    }

                    ContentState.ERROR -> {
                        OrdersMessage(contentState = ContentState.ERROR)
                    }

                    ContentState.EMPTY -> {
                        OrdersMessage(contentState = ContentState.EMPTY)
                    }
                }
            }
        }
    }
}

@Composable
fun OrdersStatusFilter(
    selectedFilter: OrdersFilter,
    onFilterSelected: (OrdersFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(top = 25.dp, start = 25.dp, end = 25.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OrderFilterChip(
            text = "All",
            selected = selectedFilter == OrdersFilter.ALL,
            onClick = { onFilterSelected(OrdersFilter.ALL) }
        )

        OrderFilterChip(
            text = "Processing",
            selected = selectedFilter == OrdersFilter.PROCESSING,
            onClick = { onFilterSelected(OrdersFilter.PROCESSING) }
        )

        OrderFilterChip(
            text = "Completed",
            selected = selectedFilter == OrdersFilter.COMPLETED,
            onClick = { onFilterSelected(OrdersFilter.COMPLETED) }
        )

        OrderFilterChip(
            text = "Canceled",
            selected = selectedFilter == OrdersFilter.CANCELED,
            onClick = { onFilterSelected(OrdersFilter.CANCELED) }
        )
    }
}

@Composable
fun OrderFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        onClick = onClick,
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                Text(text)
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        ),
        selected = selected,
        shape = RoundedCornerShape(8.dp),
        border = null
    )
}


@Composable
fun OrdersMessage(
    contentState: ContentState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = when (contentState) {
            ContentState.ERROR -> "Something went wrong"
            ContentState.EMPTY -> "No orders yet"
            ContentState.LOADING -> "Loading..."
            else -> ""
        }


        if (contentState == ContentState.LOADING) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = text,
                    modifier = Modifier.padding(25.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        } else {
            Text(
                text = text,
                modifier = Modifier.padding(25.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun OrdersList(
    orders: List<OrderUiModel>,
    selectedFilter: OrdersFilter
) {
    val filteredOrders = when (selectedFilter) {
        OrdersFilter.ALL -> orders
        OrdersFilter.PROCESSING -> orders.filterByStatus(OrdersFilter.PROCESSING)
        OrdersFilter.COMPLETED -> orders.filterByStatus(OrdersFilter.COMPLETED)
        OrdersFilter.CANCELED -> orders.filterByStatus(OrdersFilter.CANCELED)
    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 25.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filteredOrders) { order ->
            OrderCard(order = order)
        }
    }
}

private fun List<OrderUiModel>.filterByStatus(statusFilter: OrdersFilter): List<OrderUiModel> {
    return filter { order -> order.statusFilter == statusFilter }
}

@Composable
fun OrderCard(order: OrderUiModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = order.date,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = order.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .background(
                            color = Color(0xFFE7F2E6), RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = order.status,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF2EAD5B),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProductImageRow(images = order.productImages)

            Text(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.End),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,

                text = order.itemsCount
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Order: #${orderNumberCreator(order.id)}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = order.total,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ProductImageRow(images: List<Int>) {
    val imageCount = images.size
    val visibleImageCount = images.take(3)
    val hiddenImages = imageCount - visibleImageCount.size

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        visibleImageCount.forEach { image ->
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }

        if (hiddenImages > 0) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$hiddenImages",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

    }
}

private fun orderNumberCreator(id: String): String {
    return id.takeLast(5)
}

@Preview(showBackground = true)
@Composable
fun OrderScreenPreview() {
    OrderScreen(
        state = OrdersUiState(
            orders = listOf(
                OrderUiModel(
                    id = "1938473",
                    date = "15 June 2023",
                    time = "10:30",
                    statusFilter = OrdersFilter.PROCESSING,
                    status = "Processing",
                    productImages = listOf(
                        R.drawable.img_product_apple,
                        R.drawable.img_product_banana,
                        R.drawable.img_product_pepper,
                        R.drawable.img_product_apple,
                        R.drawable.img_product_banana
                    ),
                    itemsCount = "5 items",
                    total = "$24.50"
                )
            ),
            contentState = ContentState.CONTENT
        ),
    )
}
