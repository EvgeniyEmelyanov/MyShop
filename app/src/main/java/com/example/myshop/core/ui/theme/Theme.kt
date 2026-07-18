package com.example.myshop.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = White,
    background = White,
    onBackground = TextPrimary,
    surface = White,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    outline = Divider,
    surfaceVariant = ButtonSecondaryBackground,
    tertiary = ItemBackground,
    onTertiary = TextTertiary

)

@Composable
fun MyShopTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = MyShopTypography,
        content = content
    )
}