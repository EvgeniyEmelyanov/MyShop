package com.example.myshop

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private val vm: ProductDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailBinding.bind(view)

        // 1) Получаем productId из аргументов (без поиска продукта во Fragment)
        val productId = arguments?.getString("productId")
        if (productId.isNullOrBlank()) {
            findNavController().popBackStack()
            return
        }

        // 2) Инициализируем VM один раз
        vm.setProductId(productId)

        // 3) Подписка на state
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        // 4) Лисенеры вешаем ОДИН раз
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
        tvProductWeight.text = state.weight
        tvProductDescription.text = state.description
        ivPicture.setImageResource(state.imageRes)

        // Quantity + price
        tvProductCount.text = state.countText
        tvProductPrice.text = state.priceText

        // Add button
        bntAddToCart.text = state.addButtonText
        bntAddToCart.isEnabled = state.isAddEnabled

        // Favourite (tint)
        val colorRes = if (state.isFavorite) R.color.red_favorite else R.color.gray_favorite
        btnAddToFavorite.imageTintList =
            ContextCompat.getColorStateList(requireContext(), colorRes)

        // Description expanded
        tvProductDescription.visibility = if (state.isDescriptionExpanded) View.VISIBLE else View.GONE
        btnToggleDescription.rotation = if (state.isDescriptionExpanded) 90f else 0f
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
