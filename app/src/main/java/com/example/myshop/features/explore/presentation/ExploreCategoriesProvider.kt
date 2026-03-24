package com.example.myshop.features.explore.presentation

import com.example.myshop.R
import com.example.myshop.domain.product.model.Category

object ExploreCategoriesProvider {

    private val exploreCategoriesList = listOf(
        ExploreCategoryUiModel(
            R.drawable.ic_fruits,
            "Fresh Fruits\n& Vegetable",
            R.color.bg_fruits,
            R.color.stroke_fruits,
            Category.FRUITS_VEGETABLES
        ),
        ExploreCategoryUiModel(
            R.drawable.ic_oil,
            "Cooking Oil\n& Ghee",
            R.color.bg_oil,
            R.color.stroke_oil,
            Category.OIL_GHEE
        ),
        ExploreCategoryUiModel(
            R.drawable.ic_meat,
            "Meat & Fish",
            R.color.bg_meat,
            R.color.stroke_meat,
            Category.MEAT_FISH
        ),
        ExploreCategoryUiModel(
            R.drawable.ic_bakery,
            "Bakery & Snacks",
            R.color.bg_bakery,
            R.color.stroke_bakery,
            Category.BAKERY_SNACKS
        ),
        ExploreCategoryUiModel(
            R.drawable.ic_dairy,
            "Dairy & Eggs",
            R.color.bg_dairy,
            R.color.stroke_dairy,
            Category.DAIRY_EGGS
        ),
        ExploreCategoryUiModel(
            R.drawable.ic_beverages,
            "Beverages",
            R.color.bg_beverages,
            R.color.stroke_beverages,
            Category.BEVERAGES
        )
    )

    fun getCategories(): List<ExploreCategoryUiModel> {
        return exploreCategoriesList

    }
}

