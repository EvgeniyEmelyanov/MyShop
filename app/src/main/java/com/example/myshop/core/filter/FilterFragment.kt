package com.example.myshop.core.filter

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.filter.FilterResultContract.FILTER_PARAMS_KEY
import com.example.myshop.databinding.FragmentFilterBinding
import com.example.myshop.domain.product.model.Category
import com.google.android.material.checkbox.MaterialCheckBox

class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFilterBinding.bind(view)

        setInsetsForFragment(binding.root, 10)

        val initialFilterParams =
            arguments?.getParcelable<FilterParams>(FILTER_PARAMS_KEY) ?: FilterParams()

        setupCategoryCheckboxes(initialFilterParams)
        setupPriceCheckboxes(initialFilterParams)
        setupPriceSingleChoice()
        binding.bntApply.setOnClickListener { applyFilter() }

    }

    private fun setupCategoryCheckboxes(initialFilterParams: FilterParams) {
        categoryCheckboxes().forEach { (checkbox, category) ->
            checkbox.text = category.displayName
            checkbox.isChecked = category in initialFilterParams.categories
        }
    }

    private fun setupPriceCheckboxes(initialFilterParams: FilterParams) {
        priceCheckboxes().forEach { (checkbox, priceSort) ->
            checkbox.text = priceSort.displayName
            checkbox.isChecked = priceSort == initialFilterParams.priceSort
        }
    }

    private fun applyFilter() {
        val filterParams = FilterParams(
            categories = collectSelectedCategories(),
            priceSort = collectSelectedPriceSort()
        )

        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            FILTER_PARAMS_KEY,
            filterParams
        )

        findNavController().popBackStack()
    }

    private fun collectSelectedCategories(): Set<Category> {
        return categoryCheckboxes()
            .filter { (checkbox, _) -> checkbox.isChecked }
            .map { (_, category) -> category }
            .toSet()
    }

    private fun collectSelectedPriceSort(): PriceSort? {
        return priceCheckboxes()
            .firstOrNull { (checkbox, _) -> checkbox.isChecked }
            ?.second
    }

    private fun setupPriceSingleChoice() {
        priceCheckboxes().forEach { (selectedCheckbox, _) ->
            selectedCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    priceCheckboxes()
                        .map { (checkbox, _) -> checkbox }
                        .filter { checkbox -> checkbox != selectedCheckbox }
                        .forEach { checkbox -> checkbox.isChecked = false }
                }
            }
        }
    }

    private fun categoryCheckboxes(): List<Pair<MaterialCheckBox, Category>> {
        return with(binding) {
            listOf(
                cbFruitsVegetable to Category.FRUITS_VEGETABLES,
                cbCookingOilGhee to Category.OIL_GHEE,
                cbMeatFish to Category.MEAT_FISH,
                cbBakerySnacks to Category.BAKERY_SNACKS,
                cbDairyEggs to Category.DAIRY_EGGS,
                cbBeverages to Category.BEVERAGES,
            )
        }
    }

    private fun priceCheckboxes(): List<Pair<MaterialCheckBox, PriceSort>> {
        return with(binding) {
            listOf(
                cbPriceLowToHigh to PriceSort.LOW_TO_HIGH,
                cbPriceHighToLow to PriceSort.HIGH_TO_LOW,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
