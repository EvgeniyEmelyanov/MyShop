package com.example.myshop.features.deliveryAddress.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.WorkOutline
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
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressUiModel
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressUiState

@Composable
fun DeliveryAddressScreen(
    state: DeliveryAddressUiState,
    onCreateClick: () -> Unit,
    onBackClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onRemoveClick: (Long) -> Unit,
    onSetDefaultClick: (Long) -> Unit
) {
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
                items = state.addresses,
                key = { address -> address.id }
            ) { address ->
                DeliveryAddressCard(
                    address = address,
                    onEditClick = { onEditClick(address.id) },
                    onRemoveClick = { onRemoveClick(address.id) },
                    onSetDefaultClick = { onSetDefaultClick(address.id) },
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
    onSetDefaultClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val visuals = when (address.type) {
        DeliveryAddressType.HOME -> AddressTypeVisuals(
            titleRes = R.string.address_type_home,
            icon = Icons.Outlined.Home,
            accentColor = MaterialTheme.colorScheme.primary,
            accentBackground = MaterialTheme.colorScheme.primaryContainer
        )

        DeliveryAddressType.WORK -> AddressTypeVisuals(
            titleRes = R.string.address_type_work,
            icon = Icons.Outlined.WorkOutline,
            accentColor = MaterialTheme.colorScheme.tertiary,
            accentBackground = MaterialTheme.colorScheme.tertiaryContainer
        )

        DeliveryAddressType.OTHER -> AddressTypeVisuals(
            titleRes = R.string.address_type_other,
            icon = Icons.Outlined.LocationOn,
            accentColor = MaterialTheme.colorScheme.secondary,
            accentBackground = MaterialTheme.colorScheme.secondaryContainer
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(
                enabled = !address.isDefault,
                onClick = onSetDefaultClick
            )
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
                icon = visuals.icon,
                iconTint = visuals.accentColor,
                backgroundColor = visuals.accentBackground
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp),
                text = stringResource(visuals.titleRes),
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
                text = buildString {
                    append(address.street)
                    append(", ")
                    append(address.house)
                    address.building?.let { append(", $it") }
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildString {
                    append(address.settlement)
                    address.apartment?.let { append(", $it") }
                },
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

private data class AddressTypeVisuals(
    val titleRes: Int,
    val icon: ImageVector,
    val accentColor: Color,
    val accentBackground: Color
)

@Preview(showBackground = true)
@Composable
fun DeliveryAddressScreenPreview() {
    DeliveryAddressScreen(
        state = DeliveryAddressUiState(),
        onCreateClick = {},
        onBackClick = {},
        onEditClick = {},
        onRemoveClick = {},
        onSetDefaultClick = {}
    )
}
