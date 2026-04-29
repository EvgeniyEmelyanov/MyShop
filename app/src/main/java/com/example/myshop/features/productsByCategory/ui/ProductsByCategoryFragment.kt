package com.example.myshop.features.productsByCategory.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.databinding.FragmentProductsByCategoryBinding
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.domain.product.model.Category
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.ProductGridAdapter
import com.example.myshop.core.ui.decoration.GridSpacingItemDecoration
import com.example.myshop.features.productsByCategory.presentation.ProductsByCategoryUiState
import com.example.myshop.features.productsByCategory.presentation.ProductsByCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        vm.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                vm.toastShown()
            }
        }
    }

    private fun render(state: ProductsByCategoryUiState) {
        productsByCategoryAdapter.submitList(state.products)
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.rvProducts.visibility = if (state.isLoading) View.GONE else View.VISIBLE
    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            R.id.action_productsByCategoryFragment_to_productDetailFragment,
            bundleOf("productId" to productId)
        )
    }

    private fun openFilter() {
        findNavController().navigate(R.id.action_productsByCategoryFragment_to_blankFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
