package com.example.myshop.features.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.example.myshop.R
import com.example.myshop.core.ui.theme.*


@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 25.dp, start = 25.dp, end = 25.dp, bottom = 30.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(width = 63.dp, height = 64.dp)
                    .clip(RoundedCornerShape(27.dp))
                    .border(
                        shape = RoundedCornerShape(27.dp), width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Evgeniy Emelyanov",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(
                        onClick = { }, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit profile",
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = "Imshuvo97@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_orders,
            title = stringResource(id = R.string.order),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_my_details,
            title = stringResource(id = R.string.my_details),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_delivery_address,
            title = stringResource(id = R.string.delivery_address),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_payment,
            title = stringResource(id = R.string.payment_methods),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_promo_code,
            title = stringResource(id = R.string.promo_code),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_bell,
            title = stringResource(id = R.string.notifications),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_help, title = stringResource(id = R.string.help), onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        AccountMenuItem(
            iconRes = R.drawable.ic_about,
            title = stringResource(id = R.string.about),
            onClick = { })

        HorizontalDivider(
            thickness = 1.dp, color = MaterialTheme.colorScheme.outline
        )

        FilledTonalButton(
            modifier = Modifier
                .padding(start = 24.dp, top = 52.dp, end = 24.dp)
                .fillMaxWidth()
                .height(67.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.primary,
            ),
            shape = RoundedCornerShape(19.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {}) {

            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
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
}

@Composable
private fun AccountMenuItem(
    iconRes: Int, title: String, onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
            .clickable(onClick = onClick)
            .padding(start = 9.dp), headlineContent = {
        Text(
            text = title, style = MaterialTheme.typography.bodyLarge
        )

    }, leadingContent = {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }, trailingContent = {
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
        SettingsScreen()
    }
}
