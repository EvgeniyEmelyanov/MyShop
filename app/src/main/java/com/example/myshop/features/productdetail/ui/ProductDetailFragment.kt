package com.example.myshop.features.productdetail.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.ContentState
import com.example.myshop.databinding.FragmentProductDetailBinding
import com.example.myshop.features.productdetail.presentation.ProductDetailUiState
import com.example.myshop.features.productdetail.presentation.ProductDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentProductDetailBinding.bind(view)

        val productId = arguments?.getString(ARG_PRODUCT_ID)
        if (productId.isNullOrBlank()) {
            findNavController().popBackStack()
            return
        }

        setInsetsForView(
            binding.clHeaderImage, additionalTopMarginDp = 0, additionalBottomMarginDp = 0
        )

        observeState()
        setupClickListeners()
        vm.setProductId(productId)
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect(::render)
            }
        }
    }

    private fun setupClickListeners() = with(binding) {
        btnToggleDescription.setOnClickListener { vm.onToggleDescription() }
        bntAddToCart.setOnClickListener { vm.onAddToCart() }
        btnAddToFavorite.setOnClickListener { vm.onAddToFavorite() }
        btnProductIncrease.setOnClickListener { vm.onPlus() }
        btnProductDecrease.setOnClickListener { vm.onMinus() }
        btnBackToFirstFragment.setOnClickListener { findNavController().popBackStack() }
        stateView.btnRetry.setOnClickListener { vm.load() }
    }

    private fun render(state: ProductDetailUiState) {
        renderProduct(state)
        renderActions(state)
        renderContentState(state.contentState)
    }

    private fun renderProduct(state: ProductDetailUiState) = with(binding) {
        tvProductTitle.text = state.title
        tvProductWeight.text = state.subtitle
        tvProductDescription.text = state.description
        ivPicture.setImageResource(state.imageRes)
        tvProductCount.text = state.countText
        tvProductPrice.text = state.price
    }

    private fun renderActions(state: ProductDetailUiState) = with(binding) {
        bntAddToCart.text = state.addButtonText
        bntAddToCart.isEnabled = state.isAddEnabled
        val iconRes = if (state.isFavorite) {
            R.drawable.ic_favourite_filled
        } else {
            R.drawable.ic_favourite_outline
        }
        btnAddToFavorite.setImageResource(iconRes)
        tvProductDescription.isVisible = state.isDescriptionExpanded
        btnToggleDescription.rotation = if (state.isDescriptionExpanded) 90f else 0f
    }

    private fun renderContentState(contentState: ContentState) = with(binding) {
        progressBar.isVisible = contentState == ContentState.LOADING
        stateView.root.isVisible =
            contentState == ContentState.ERROR || contentState == ContentState.EMPTY
        stateView.tvStateMessage.setText(
            if (contentState == ContentState.ERROR) {
                R.string.error_loading_product
            } else {
                R.string.empty_product
            }
        )
        stateView.btnRetry.isVisible = contentState == ContentState.ERROR
        scrollContent.isVisible = contentState == ContentState.CONTENT
        bntAddToCart.isVisible = contentState == ContentState.CONTENT
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val ARG_PRODUCT_ID = "productId"
    }
}
