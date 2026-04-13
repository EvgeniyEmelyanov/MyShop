package com.example.myshop.features.shop.presentation

import com.example.myshop.R

object GroceriesCategoriesProvider {

    private val groceriesCategories = listOf(
        GroceriesCategoryUiModel(
            title = "Pulses",
            imageRes = R.drawable.pulses_picture,
            backgroundColorRes = R.color.bg_grocery_pulses
        ),
        GroceriesCategoryUiModel(
            title = "Rice",
            imageRes = R.drawable.rice_pictute,
            backgroundColorRes = R.color.bg_grocery_rice
        ),
        GroceriesCategoryUiModel(
            title = "Meat",
            imageRes = R.drawable.rice_pictute,
            backgroundColorRes = R.color.bg_grocery_meat
        )
    )

    fun getCategories(): List<GroceriesCategoryUiModel> = groceriesCategories

}



