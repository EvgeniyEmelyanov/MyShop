package com.example.myshop.features.explore.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.ProductGridAdapter
import com.example.myshop.core.decoration.GridSpacingItemDecoration
import com.example.myshop.databinding.FragmentExploreBinding
import com.example.myshop.domain.product.model.Category
import com.example.myshop.features.explore.presentation.ExploreUiState
import com.example.myshop.features.explore.presentation.ExploreViewModel
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.FilterResultContract.FILTER_PARAMS_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreFragment : BaseFragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val vm: ExploreViewModel by viewModels()
    private lateinit var exploreCategoriesAdapter: ExploreBannerAdapter

    private lateinit var productsAdapter: ProductGridAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExploreBinding.bind(view)

        setInsetsForView(view, additionalTopMarginDp = 10)

        setupAdapter()

        setupList()

        observeState()

        setupSearch()

        binding.btnFilter.setOnClickListener {
            openFilter()
        }

        if (savedInstanceState == null) {
            vm.load()
        }
    }

    private fun setupAdapter() {
        exploreCategoriesAdapter = ExploreBannerAdapter(
            onClick = { banner ->
                openProductsByCategory(banner.category)
            })

        productsAdapter = ProductGridAdapter(onRootClick = { productId ->
            openProductDetail(productId)
        }, onAddBtnClick = { productId ->
            vm.onAddToCart(productId)
        })

    }

    private fun setupList() {
        binding.rvExploreBanner.apply {
            adapter = exploreCategoriesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 2, spacing = requireContext().dpToPx(15), includeEdge = false
                    )
                )
            }
        }
    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        vm.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                vm.toastShown()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<FilterParams>(
                FILTER_PARAMS_KEY
            )?.observe(viewLifecycleOwner) { filterParams ->
                vm.onFilterChanged(filterParams)
            }
    }

    private fun render(state: ExploreUiState) {
        if (state.isSearchMode) {
            if (binding.rvExploreBanner.adapter != productsAdapter) {
                binding.rvExploreBanner.adapter = productsAdapter
            }

            productsAdapter.submitList(state.products)

        } else {
            if (binding.rvExploreBanner.adapter != exploreCategoriesAdapter) {
                binding.rvExploreBanner.adapter = exploreCategoriesAdapter
            }

            exploreCategoriesAdapter.submitList(state.categories)
        }

        binding.btnFilter.visibility = if (state.isSearchMode) View.VISIBLE else View.GONE
    }


    private fun openProductsByCategory(category: Category) {
        findNavController().navigate(
            R.id.action_exploreFragment_to_productsByCategoryFragment,
            bundleOf("category" to category.name)
        )
    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            R.id.action_exploreFragment_to_productDetailFragment,
            bundleOf("productId" to productId)
        )
    }

    private fun openFilter() {
        val currentState = vm.state.value?.filterParams ?: FilterParams()

        findNavController().navigate(
            R.id.action_exploreFragment_to_filterFragment,
            bundleOf(FILTER_PARAMS_KEY to currentState)
        )
    }

    private fun setupSearch() {
        binding.editText2.doAfterTextChanged { text ->
            vm.onSearchQueryChanged(text?.toString().orEmpty())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}