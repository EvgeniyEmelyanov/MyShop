package com.example.myshop.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myshop.R

val Gilroy = FontFamily(
    Font(
        resId = R.font.gilroy_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.gilroy_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.gilroy_black,
        weight = FontWeight.Black
    ),
    Font(
        resId = R.font.gilroy_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.gilroy_regular,
        weight = FontWeight.Normal
    )
)

val MyShopTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 18.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Gilroy,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )
)

