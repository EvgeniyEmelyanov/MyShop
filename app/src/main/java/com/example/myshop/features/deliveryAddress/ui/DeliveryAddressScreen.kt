package com.example.myshop.features.deliveryAddress.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshop.R

@Composable
fun DeliveryAddressScreen(
    onCreateClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val addresses = listOf(
        DeliveryAddressUiModel(
            id = "home",
            title = "Home",
            city = "Minsk",
            street = "House 12, Road 5, Block A",
            apartment = "Apartment 24",
            icon = Icons.Outlined.Home,
            isDefault = true,
            accentColor = Color(0xFFFF6B57),
            accentBackground = Color(0xFFFFEDEA)
        ),
        DeliveryAddressUiModel(
            id = "office",
            title = "Office",
            city = "Minsk",
            street = "Plot 45, Road 12, Block B",
            apartment = "Office 305",
            icon = Icons.Outlined.Business,
            isDefault = false,
            accentColor = Color(0xFF8B63F6),
            accentBackground = Color(0xFFF0E9FF)
        ),
        DeliveryAddressUiModel(
            id = "other",
            title = "Other",
            city = "Minsk",
            street = "House 7, Road 3, Block C",
            apartment = "Apartment 18",
            icon = Icons.Outlined.LocationOn,
            isDefault = false,
            accentColor = MaterialTheme.colorScheme.primary,
            accentBackground = Color(0xFFEAF6EF)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DeliveryAddressToolbar(
            onCreateClick = onCreateClick,
            onBackClick = onBackClick
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = addresses,
                key = { address -> address.id }
            ) { address ->
                DeliveryAddressCard(
                    address = address,
                    onEditClick = {},
                    onRemoveClick = {},
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun DeliveryAddressToolbar(
    onCreateClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .padding(top = 25.dp, bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.cd_back),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = stringResource(id = R.string.delivery_address_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            onClick = onCreateClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.cd_create_address),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun DeliveryAddressCard(
    address: DeliveryAddressUiModel,
    onEditClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AddressIcon(
                icon = address.icon,
                iconTint = address.accentColor,
                backgroundColor = address.accentBackground
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp),
                text = address.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (address.isDefault) {
                DefaultAddressBadge()
            }
        }

        Column(
            modifier = Modifier.padding(start = 48.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = address.street,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${address.city}, ${address.apartment}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AddressActionButton(
                text = stringResource(id = R.string.edit),
                icon = Icons.Outlined.Edit,
                contentColor = MaterialTheme.colorScheme.primary,
                onClick = onEditClick,
                modifier = Modifier.weight(1f)
            )

            AddressActionButton(
                text = stringResource(id = R.string.remove),
                icon = Icons.Outlined.DeleteOutline,
                contentColor = MaterialTheme.colorScheme.error,
                onClick = onRemoveClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AddressIcon(
    icon: ImageVector,
    iconTint: Color,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(22.dp),
            imageVector = icon,
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
private fun DefaultAddressBadge() {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFEAF6EF))
            .padding(horizontal = 14.dp, vertical = 7.dp),
        text = stringResource(id = R.string.default_address),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun AddressActionButton(
    text: String,
    icon: ImageVector,
    contentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        modifier = modifier.height(38.dp),
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, contentColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private data class DeliveryAddressUiModel(
    val id: String,
    val title: String,
    val city: String,
    val street: String,
    val apartment: String,
    val icon: ImageVector,
    val isDefault: Boolean,
    val accentColor: Color,
    val accentBackground: Color
)

@Preview(showBackground = true)
@Composable
fun DeliveryAddressScreenPreview() {
    DeliveryAddressScreen(
        onCreateClick = {},
        onBackClick = {}
    )
}
