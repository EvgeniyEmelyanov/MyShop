package com.example.myshop.features.productsByCategory.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.databinding.FragmentProductsByCategoryBinding
import com.example.myshop.core.ui.dpToPx
import com.example.myshop.domain.product.model.Category
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.filter.FilterParams
import com.example.myshop.core.filter.FilterResultContract.FILTER_PARAMS_KEY
import com.example.myshop.core.filter.FilterResultContract.INITIAL_FILTER_PARAMS_KEY
import com.example.myshop.core.ui.ProductGridAdapter
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.decoration.GridSpacingItemDecoration
import com.example.myshop.features.productsByCategory.presentation.ProductsByCategoryUiState
import com.example.myshop.features.productsByCategory.presentation.ProductsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsByCategoryFragment : BaseFragment(R.layout.fragment_products_by_category) {
    private lateinit var productsByCategoryAdapter: ProductGridAdapter
    private var _binding: FragmentProductsByCategoryBinding? = null
    private val binding get() = _binding!!
    private val vm: ProductsByCategoryViewModel by viewModels ()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductsByCategoryBinding.bind(view)

        val categoryStr = arguments?.getString("category") ?: return
        val category = runCatching { Category.valueOf(categoryStr) }.getOrNull() ?: run {
            findNavController().popBackStack()
            return
        }

        setInsetsForFragment(view, additionalTopMarginDp = 10)

        setupAdapter()

        setupList()

        observeState()

        vm.setCategory(category)

        binding.btnBackToFirstFragment.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnFilter.setOnClickListener {
            openFilter()
        }

        binding.stateView.btnRetry.setOnClickListener {
            vm.load()
        }

        binding.tvProductGroupTitle.text = category.displayName

    }

    private fun setupAdapter() {
        productsByCategoryAdapter = ProductGridAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddProduct(productId) }

        )
    }

    private fun setupList() {
        binding.rvProducts.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)

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
        val filterResults: StateFlow<FilterParams>? = findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow(FILTER_PARAMS_KEY, vm.state.value.filterParams)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    vm.state.collect(::render)
                }

                launch {
                    vm.toastMessage.collect { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }

                if (filterResults != null) {
                    launch {
                        filterResults.collect { filterParams ->
                            if (vm.state.value.filterParams != filterParams) {
                                vm.onFilterChanged(filterParams)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun render(state: ProductsByCategoryUiState) {
        productsByCategoryAdapter.submitList(state.products)

        binding.progressBar.visibility =
            if (state.contentState == ContentState.LOADING) View.VISIBLE else View.GONE

        binding.rvProducts.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE

        binding.stateView.root.visibility =
            if (state.contentState == ContentState.EMPTY ||
                state.contentState == ContentState.ERROR
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.stateView.tvStateMessage.setText(
            if (state.contentState == ContentState.ERROR) {
                R.string.error_loading_products
            } else {
                R.string.empty_products
            }
        )

        binding.stateView.btnRetry.visibility =
            if (state.contentState == ContentState.ERROR) View.VISIBLE else View.GONE
    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            R.id.action_productsByCategoryFragment_to_productDetailFragment,
            Bundle().apply {
                putString("productId", productId)
            }
        )
    }

    private fun openFilter() {
        val currentFilterParams = vm.state.value.filterParams

        findNavController().navigate(R.id.action_productsByCategoryFragment_to_filterForProductsByCategory)

        findNavController().getBackStackEntry(R.id.filterForProductsByCategory)
            .savedStateHandle[INITIAL_FILTER_PARAMS_KEY] = currentFilterParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
