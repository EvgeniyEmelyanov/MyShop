package com.example.myshop.features.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme

enum class SettingsMenuAction {
    Orders,
    MyDetails,
    DeliveryAddress,
    PaymentMethods,
    PromoCode,
    Notifications,
    Help,
    About
}

private data class SettingsMenuItem(
    val iconRes: Int,
    val titleRes: Int,
    val action: SettingsMenuAction
)

private val settingsMenuItems = listOf(
    SettingsMenuItem(
        iconRes = R.drawable.ic_orders,
        titleRes = R.string.order,
        action = SettingsMenuAction.Orders
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_my_details,
        titleRes = R.string.my_details,
        action = SettingsMenuAction.MyDetails
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_delivery_address,
        titleRes = R.string.delivery_address,
        action = SettingsMenuAction.DeliveryAddress
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_payment,
        titleRes = R.string.payment_methods,
        action = SettingsMenuAction.PaymentMethods
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_promo_code,
        titleRes = R.string.promo_code,
        action = SettingsMenuAction.PromoCode
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_bell,
        titleRes = R.string.notifications,
        action = SettingsMenuAction.Notifications
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_help,
        titleRes = R.string.help,
        action = SettingsMenuAction.Help
    ),
    SettingsMenuItem(
        iconRes = R.drawable.ic_about,
        titleRes = R.string.about,
        action = SettingsMenuAction.About
    )
)

@Composable
fun SettingsScreen(
    userName: String,
    userEmail: String,
    onSaveProfileClick: (String, String) -> Unit,
    onMenuItemClick: (SettingsMenuAction) -> Unit,
    onLogoutClick: () -> Unit
) {
    var isEditProfileDialogVisible by remember { mutableStateOf(false) }

    var editableName by remember(userName) {
        mutableStateOf(userName)
    }

    var editableEmail by remember(userEmail) {
        mutableStateOf(userEmail)
    }

    val isSaveEnabled = editableName.isNotBlank() && editableEmail.isNotBlank()

    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 25.dp,
                    start = 25.dp,
                    end = 25.dp,
                    bottom = 30.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 63.dp, height = 64.dp)
                    .clip(RoundedCornerShape(27.dp))
                    .border(
                        shape = RoundedCornerShape(27.dp),
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(
                        onClick = {
                            isEditProfileDialogVisible = true
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(id = R.string.cd_edit_profile),
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        settingsMenuItems.forEach { item ->
            AccountMenuItem(
                iconRes = item.iconRes,
                title = stringResource(id = item.titleRes),
                onClick = { onMenuItemClick(item.action) }
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        }

        FilledTonalButton(
            modifier = Modifier
                .padding(start = 24.dp, top = 25.dp, end = 24.dp)
                .fillMaxWidth()
                .height(67.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(19.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = onLogoutClick
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 24.dp)
                        .size(ButtonDefaults.IconSize)
                )

                Text(
                    text = stringResource(id = R.string.log_out),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }

    if (isEditProfileDialogVisible) {
        EditProfileDialog(
            editableName = editableName,
            editableEmail = editableEmail,
            isSaveEnabled = isSaveEnabled,
            onNameChange = { newName ->
                editableName = newName
            },
            onEmailChange = { newEmail ->
                editableEmail = newEmail
            },
            onDismiss = {
                isEditProfileDialogVisible = false
            },
            onSaveClick = {
                onSaveProfileClick(
                    editableName.trim(),
                    editableEmail.trim()
                )
                isEditProfileDialogVisible = false
            }
        )
    }
}

@Composable
private fun EditProfileDialog(
    editableName: String,
    editableEmail: String,
    isSaveEnabled: Boolean,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.edit_profile))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = editableName,
                    onValueChange = onNameChange,
                    label = {
                        Text(text = stringResource(id = R.string.name))
                    },
                    singleLine = true
                )

                OutlinedTextField(
                    value = editableEmail,
                    onValueChange = onEmailChange,
                    label = {
                        Text(text = stringResource(id = R.string.email))
                    },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = isSaveEnabled,
                onClick = onSaveClick
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@Composable
private fun AccountMenuItem(
    iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .clickable(onClick = onClick)
            .padding(start = 9.dp),
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingContent = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    MyShopTheme {
        SettingsScreen(
            userName = "Type your name",
            userEmail = "Type your email",
            onLogoutClick = {},
            onMenuItemClick = {},
            onSaveProfileClick = { _, _ -> }
        )
    }
}
