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
import com.example.myshop.domain.product.model.Brand
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

        setupBrandCheckboxes(initialFilterParams)
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

    private fun setupBrandCheckboxes(initialFilterParams: FilterParams) {
        brandCheckboxes().forEach { (checkbox, brand) ->
            checkbox.text = brand.displayName
            checkbox.isChecked = brand in initialFilterParams.brands
        }
    }

    private fun applyFilter() {
        val filterParams = FilterParams(
            brands = collectSelectedBrands(),
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

    private fun collectSelectedBrands(): Set<Brand> {
        return brandCheckboxes().filter { (checkbox, _) -> checkbox.isChecked }
            .map { (_, brand) -> brand }.toSet()
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

    private fun brandCheckboxes(): List<Pair<MaterialCheckBox, Brand>> {
        return with(binding) {
            listOf(
                cbBrandFreshfield to Brand.FRESHFIELD,
                cbBrandOrchardLane to Brand.ORCHARD_LANE,
                cbBrandMeadowDairy to Brand.MEADOW_DAIRY,
                cbBrandSunnyHen to Brand.SUNNY_HEN,
                cbBrandClearspring to Brand.CLEARSPRING,
                cbBrandNorthRoast to Brand.NORTH_ROAST,
                cbBrandGoldenHarvest to Brand.GOLDEN_HARVEST,
                cbBrandPurepress to Brand.PUREPRESS,
                cbBrandFarmstead to Brand.FARMSTEAD,
                cbBrandHarborCatch to Brand.HARBOR_CATCH,
                cbBrandBakerStreet to Brand.BAKER_STREET,
                cbBrandCrunchClub to Brand.CRUNCH_CLUB,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
