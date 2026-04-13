package com.example.myshop.features.productdetail.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.databinding.FragmentProductDetailBinding
import com.example.myshop.features.productdetail.presentation.ProductDetailUiState
import com.example.myshop.features.productdetail.presentation.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!


    private val vm: ProductDetailViewModel by viewModels ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductDetailBinding.bind(view)

        val productId = arguments?.getString("productId")
        if (productId.isNullOrBlank()) {
            findNavController().popBackStack()
            return
        }

        setInsetsForView(
            binding.clHeaderImage, additionalTopMarginDp = 0, additionalBottomMarginDp = 0
        )

        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }


        vm.setProductId(productId)

        // 4) Лисенеры
        binding.btnToggleDescription.setOnClickListener { vm.onToggleDescription() }
        binding.bntAddToCart.setOnClickListener { vm.onAddToCart() }
        binding.btnAddToFavorite.setOnClickListener { vm.onAddToFavorite() }
        binding.btnProductIncrease.setOnClickListener { vm.onPlus() }
        binding.btnProductDecrease.setOnClickListener { vm.onMinus() }
        binding.btnBackToFirstFragment.setOnClickListener { findNavController().popBackStack() }
    }

    private fun render(state: ProductDetailUiState) = with(binding) {
        // Product
        tvProductTitle.text = state.title
        tvProductWeight.text = state.subtitle
        tvProductDescription.text = state.description
        ivPicture.setImageResource(state.imageRes)

        // Quantity + price
        tvProductCount.text = state.countText
        tvProductPrice.text = state.price

        // Add button
        bntAddToCart.text = state.addButtonText
        bntAddToCart.isEnabled = state.isAddEnabled

        // Favourite (tint)
        val iconRes = if (state.isFavorite) {
            R.drawable.ic_btn_favourite_red // Закрашенная иконка
        } else {
            R.drawable.ic_btn_favourite // Контурная иконка
        }

        btnAddToFavorite.setImageResource(iconRes)

        // Description expanded
        tvProductDescription.visibility =
            if (state.isDescriptionExpanded) View.VISIBLE else View.GONE

        btnToggleDescription.rotation = if (state.isDescriptionExpanded) 90f else 0f

        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        scrollContent.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        bntAddToCart.visibility = if (state.isLoading) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}