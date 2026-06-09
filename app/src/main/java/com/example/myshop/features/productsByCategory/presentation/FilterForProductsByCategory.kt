package com.example.myshop.features.productsByCategory.presentation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.FilterResultContract
import com.example.myshop.core.filter.PriceSort
import com.example.myshop.databinding.FragmentFilterForProductsByCategoryBinding
import com.google.android.material.checkbox.MaterialCheckBox

class FilterForProductsByCategory :
    BaseFragment(R.layout.fragment_filter_for_products_by_category) {

    private var _binding: FragmentFilterForProductsByCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFilterForProductsByCategoryBinding.bind(view)

        setInsetsForFragment(binding.root, 10)

        val initialFilterParams = getInitialFilterParams()

        setupPriceCheckboxes(initialFilterParams)
        setupPriceSingleChoice()
        setupActions()
    }

    private fun getInitialFilterParams(): FilterParams {
        return findNavController().currentBackStackEntry?.savedStateHandle?.get<FilterParams>(
            FilterResultContract.INITIAL_FILTER_PARAMS_KEY
        ) ?: FilterParams()
    }

    private fun setupActions() {
        with(binding) {
            bntApply.setOnClickListener { applyFilter() }
            closeFilter.setOnClickListener { findNavController().popBackStack() }
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
            priceSort = collectSelectedPriceSort()
        )

        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            FilterResultContract.FILTER_PARAMS_KEY, filterParams
        )

        findNavController().popBackStack()
    }

    private fun collectSelectedPriceSort(): PriceSort? {
        return priceCheckboxes().firstOrNull { (checkbox, _) -> checkbox.isChecked }?.second
    }

    private fun setupPriceSingleChoice() {
        priceCheckboxes().forEach { (selectedCheckbox, _) ->
            selectedCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    priceCheckboxes().map { (checkbox, _) -> checkbox }
                        .filter { checkbox -> checkbox != selectedCheckbox }
                        .forEach { checkbox -> checkbox.isChecked = false }
                }
            }
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
