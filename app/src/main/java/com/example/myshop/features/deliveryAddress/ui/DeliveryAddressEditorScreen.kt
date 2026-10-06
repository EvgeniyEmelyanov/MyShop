package com.example.myshop.features.deliveryAddress.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshop.R
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressEditorUiState

@Composable
fun DeliveryAddressEditorScreen(
    state: DeliveryAddressEditorUiState,
    onBackClick: () -> Unit,
    onTypeChanged: (DeliveryAddressType) -> Unit,
    onSettlementChanged: (String) -> Unit,
    onStreetChanged: (String) -> Unit,
    onHouseChanged: (String) -> Unit,
    onBuildingChanged: (String) -> Unit,
    onApartmentChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .imePadding()
    ) {
        DeliveryAddressEditorToolbar(
            isEditing = state.isEditing,
            onBackClick = onBackClick
        )

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.loadFailed -> {
                AddressLoadError(
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            else -> {
                AddressEditorForm(
                    state = state,
                    onTypeChanged = onTypeChanged,
                    onSettlementChanged = onSettlementChanged,
                    onStreetChanged = onStreetChanged,
                    onHouseChanged = onHouseChanged,
                    onBuildingChanged = onBuildingChanged,
                    onApartmentChanged = onApartmentChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                if (state.saveFailed) {
                    Text(
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 8.dp),
                        text = stringResource(R.string.delivery_address_save_error),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Button(
                    onClick = onSaveClick,
                    enabled = !state.isSaving,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp, vertical = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.save_address),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DeliveryAddressEditorToolbar(
    isEditing: Boolean,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 13.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.cd_back),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = stringResource(
                if (isEditing) R.string.edit_delivery_address else R.string.add_delivery_address
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.size(48.dp))
    }
}

@Composable
private fun AddressEditorForm(
    state: DeliveryAddressEditorUiState,
    onTypeChanged: (DeliveryAddressType) -> Unit,
    onSettlementChanged: (String) -> Unit,
    onStreetChanged: (String) -> Unit,
    onHouseChanged: (String) -> Unit,
    onBuildingChanged: (String) -> Unit,
    onApartmentChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 25.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = stringResource(R.string.address_type),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        item {
            AddressTypeSelector(
                selectedType = state.type,
                onTypeChanged = onTypeChanged
            )
        }

        item {
            AddressTextField(
                value = state.settlement,
                onValueChange = onSettlementChanged,
                label = stringResource(R.string.settlement),
                showError = state.showValidationErrors && state.settlement.isBlank()
            )
        }

        item {
            AddressTextField(
                value = state.street,
                onValueChange = onStreetChanged,
                label = stringResource(R.string.street),
                showError = state.showValidationErrors && state.street.isBlank()
            )
        }

        item {
            AddressTextField(
                value = state.house,
                onValueChange = onHouseChanged,
                label = stringResource(R.string.house),
                showError = state.showValidationErrors && state.house.isBlank()
            )
        }

        item {
            AddressTextField(
                value = state.building,
                onValueChange = onBuildingChanged,
                label = stringResource(R.string.building_optional)
            )
        }

        item {
            AddressTextField(
                value = state.apartment,
                onValueChange = onApartmentChanged,
                label = stringResource(R.string.apartment_optional)
            )
        }
    }
}

@Composable
private fun AddressTypeSelector(
    selectedType: DeliveryAddressType,
    onTypeChanged: (DeliveryAddressType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AddressTypeChip(
            type = DeliveryAddressType.HOME,
            selectedType = selectedType,
            title = stringResource(R.string.address_type_home),
            icon = Icons.Outlined.Home,
            onTypeChanged = onTypeChanged,
            modifier = Modifier.weight(1f)
        )
        AddressTypeChip(
            type = DeliveryAddressType.WORK,
            selectedType = selectedType,
            title = stringResource(R.string.address_type_work),
            icon = Icons.Outlined.WorkOutline,
            onTypeChanged = onTypeChanged,
            modifier = Modifier.weight(1f)
        )
        AddressTypeChip(
            type = DeliveryAddressType.OTHER,
            selectedType = selectedType,
            title = stringResource(R.string.address_type_other),
            icon = Icons.Outlined.LocationOn,
            onTypeChanged = onTypeChanged,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AddressTypeChip(
    type: DeliveryAddressType,
    selectedType: DeliveryAddressType,
    title: String,
    icon: ImageVector,
    onTypeChanged: (DeliveryAddressType) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selectedType == type,
        onClick = { onTypeChanged(type) },
        label = { Text(text = title) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        },
        modifier = modifier
    )
}

@Composable
private fun AddressTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    showError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        singleLine = true,
        isError = showError,
        supportingText = if (showError) {
            { Text(text = stringResource(R.string.required_field)) }
        } else {
            null
        },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun AddressLoadError(
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.delivery_address_load_error),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Button(
            onClick = onRetryClick,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DeliveryAddressEditorScreenPreview() {
    DeliveryAddressEditorScreen(
        state = DeliveryAddressEditorUiState(),
        onBackClick = {},
        onTypeChanged = {},
        onSettlementChanged = {},
        onStreetChanged = {},
        onHouseChanged = {},
        onBuildingChanged = {},
        onApartmentChanged = {},
        onSaveClick = {},
        onRetryClick = {}
    )
}
