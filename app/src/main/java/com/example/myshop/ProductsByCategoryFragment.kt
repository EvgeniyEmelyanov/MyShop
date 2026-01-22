package com.example.myshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.databinding.FragmentProductsByCategoryBinding
import com.evgeniyemelyanov.core.ui.dpToPx

class ProductsByCategoryFragment : Fragment(R.layout.fragment_products_by_category) {
    private lateinit var productsByCategoryAdapter: ProductsByCategoryAdapter
    private var _binding: FragmentProductsByCategoryBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductsByCategoryBinding.bind(view)

        val categoryStr = arguments?.getString("category") ?: return
        val category = runCatching { Category.valueOf(categoryStr) }.getOrNull()
            ?: run {
                findNavController().popBackStack()
                return
            }


        productsByCategoryAdapter = ProductsByCategoryAdapter(
            onRootClick = { productId ->
                val args = Bundle().apply {
                    putString("productId", productId)
                }
                findNavController().navigate(
                    R.id.action_productsByCategoryFragment_to_productDetailFragment,
                    args
                )
            },
            onAddBtnClick = { productId ->
                AppState.cartManager.addToCart(productId)
            }
        )



        binding.rvProducts.apply {
            adapter = productsByCategoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 2,
                        spacing = requireContext().dpToPx(15),
                        includeEdge = false
                    )
                )
            }
        }

        binding.tvProductGroupTitle.text = category.displayName

        val products = ProductStore.byCategory(category)
        productsByCategoryAdapter.submitList(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}