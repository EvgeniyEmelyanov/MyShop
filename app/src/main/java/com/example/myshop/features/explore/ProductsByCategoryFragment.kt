package com.example.myshop.features.explore

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.databinding.FragmentProductsByCategoryBinding
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.domain.product.model.Category
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.decoration.GridSpacingItemDecoration
import com.example.myshop.data.product.datasource.ProductStore
import com.example.myshop.di.AppGraph
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.product.model.AmountType

class ProductsByCategoryFragment : BaseFragment(R.layout.fragment_products_by_category) {
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

        setInsetsForFragment(view, additionalTopMarginDp = 10)

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
                val p = AppGraph.getProductByIdUseCase.getById(productId) ?: return@ProductsByCategoryAdapter
                val start = when (p.amountType) {
                    AmountType.PIECE -> Amount.Piece(1)
                    AmountType.WEIGHT -> Amount.Grams(1000)
                }
                AppGraph.addProductToCartUseCase.addProduct(productId, start)
            }
        )

        binding.btnBackToFirstFragment.setOnClickListener {
            findNavController().popBackStack()
        }


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
