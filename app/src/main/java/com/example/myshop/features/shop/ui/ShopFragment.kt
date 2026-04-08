package com.example.myshop.features.shop.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.databinding.FragmentShopBinding
import com.example.myshop.features.shop.presentation.ShopUiState
import com.example.myshop.features.shop.presentation.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopFragment : BaseFragment(R.layout.fragment_shop) {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val vm: ShopViewModel by viewModels ()

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var exclusiveAdapter: ProductHorizontalAdapter
    private lateinit var bestSellingAdapter: ProductHorizontalAdapter
    private lateinit var groceriesProductsAdapter: ProductHorizontalAdapter
    private lateinit var groceriesAdapter: GroceriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopBinding.bind(view)


        setInsetsForView(binding.ivCarrot, additionalTopMarginDp = 10)

        setupAdapters()
        setupLists()
        observeState()

        vm.load()
    }

    private fun setupAdapters() {
        bannerAdapter = BannerAdapter()

        exclusiveAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddProduct(productId) }
        )

        bestSellingAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddProduct(productId) }
        )

        groceriesProductsAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddProduct(productId) }
        )

        groceriesAdapter = GroceriesAdapter()
    }

    private fun setupLists() = with(binding) {
        vpBanners.adapter = bannerAdapter


        rvExclusiveOffer.apply {
            adapter = exclusiveAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15)
                    )
                )
            }
        }

        rvBestSellingProducts.apply {
            adapter = bestSellingAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15)
                    )
                )
            }
        }

        rvGroceriesProductCard.apply {
            adapter = groceriesProductsAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15)
                    )
                )
            }
        }

        rvGroceries.apply {
            adapter = groceriesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
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


    private fun render(state: ShopUiState) {
        bannerAdapter.submitList(state.banners)
        exclusiveAdapter.submitList(state.exclusiveOffers)
        bestSellingAdapter.submitList(state.bestSelling)
        groceriesProductsAdapter.submitList(state.groceriesProducts)
        groceriesAdapter.submitList(state.groceriesCategories)
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        binding.contentContainer.visibility = if (state.isLoading) View.GONE else View.VISIBLE
    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            R.id.action_shopFragment_to_productDetailFragment,
            bundleOf("productId" to productId)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
