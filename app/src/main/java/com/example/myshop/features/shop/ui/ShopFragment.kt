package com.example.myshop.features.shop.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.CommonProductUiModel
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.decoration.GridSpacingItemDecoration
import com.example.myshop.core.ui.dpToPx
import com.example.myshop.databinding.FragmentShopBinding
import com.example.myshop.features.productsByCategory.ui.ProductsByCategoryAdapter
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
    private lateinit var exclusiveGridAdapter: ProductsByCategoryAdapter
    private lateinit var bestSellingGridAdapter: ProductsByCategoryAdapter
    private lateinit var groceriesProductsGridAdapter: ProductsByCategoryAdapter
    private lateinit var groceriesAdapter: GroceriesAdapter
    private var exclusiveListMode = ProductListMode.HORIZONTAL
    private var bestSellingListMode = ProductListMode.HORIZONTAL
    private var groceriesProductsListMode = ProductListMode.HORIZONTAL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopBinding.bind(view)

        setInsetsForView(binding.ivCarrot, additionalTopMarginDp = 10)

        setupAdapters()
        setupLists()
        setupSeeAllClicks()
        observeState()

        binding.stateView?.btnRetry?.setOnClickListener {
            vm.load()
        }

        vm.load()
    }

    private fun setupAdapters() {
        bannerAdapter = BannerAdapter()

        exclusiveAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        bestSellingAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        groceriesProductsAdapter = ProductHorizontalAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        exclusiveGridAdapter = ProductsByCategoryAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        bestSellingGridAdapter = ProductsByCategoryAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        groceriesProductsGridAdapter = ProductsByCategoryAdapter(
            onRootClick = { productId -> openProductDetail(productId) },
            onAddBtnClick = { productId -> vm.onAddToCart(productId) }
        )

        groceriesAdapter = GroceriesAdapter()
    }

    private fun setupLists() = with(binding) {
        vpBanners.adapter = bannerAdapter


        setupHorizontalProductsList(rvExclusiveOffer, exclusiveAdapter)
        setupHorizontalProductsList(rvBestSellingProducts, bestSellingAdapter)
        setupHorizontalProductsList(rvGroceriesProductCard, groceriesProductsAdapter)

        rvGroceries.apply {
            adapter = groceriesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun setupSeeAllClicks() = with(binding) {
        tvSeeAllFirst.setOnClickListener {
            exclusiveListMode = exclusiveListMode.toggle()
            render(vm.state.value ?: ShopUiState())
        }

        tvSeeAllSecond.setOnClickListener {
            bestSellingListMode = bestSellingListMode.toggle()
            render(vm.state.value ?: ShopUiState())
        }

        tvSeeAllThird.setOnClickListener {
            groceriesProductsListMode = groceriesProductsListMode.toggle()
            render(vm.state.value ?: ShopUiState())
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
        renderProductList(
            recyclerView = binding.rvExclusiveOffer,
            mode = exclusiveListMode,
            horizontalAdapter = exclusiveAdapter,
            gridAdapter = exclusiveGridAdapter,
            items = state.exclusiveOffers
        )
        renderProductList(
            recyclerView = binding.rvBestSellingProducts,
            mode = bestSellingListMode,
            horizontalAdapter = bestSellingAdapter,
            gridAdapter = bestSellingGridAdapter,
            items = state.bestSelling
        )
        renderProductList(
            recyclerView = binding.rvGroceriesProductCard,
            mode = groceriesProductsListMode,
            horizontalAdapter = groceriesProductsAdapter,
            gridAdapter = groceriesProductsGridAdapter,
            items = state.groceriesProducts
        )
        groceriesAdapter.submitList(state.groceriesCategories)
        binding.tvSeeAllFirst.text = exclusiveListMode.actionText()
        binding.tvSeeAllSecond.text = bestSellingListMode.actionText()
        binding.tvSeeAllThird.text = groceriesProductsListMode.actionText()
        binding.progressBar.visibility =
            if (state.contentState == ContentState.LOADING) View.VISIBLE else View.GONE
        binding.contentContainer.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE
        binding.stateView?.let { stateView ->
            stateView.root.visibility =
                if (state.contentState == ContentState.EMPTY ||
                    state.contentState == ContentState.ERROR
                ) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            stateView.tvStateMessage.setText(
                if (state.contentState == ContentState.ERROR) {
                    R.string.error_loading_products
                } else {
                    R.string.empty_products
                }
            )
            stateView.btnRetry.visibility =
                if (state.contentState == ContentState.ERROR) View.VISIBLE else View.GONE
        }
    }

    private fun renderProductList(
        recyclerView: RecyclerView,
        mode: ProductListMode,
        horizontalAdapter: ProductHorizontalAdapter,
        gridAdapter: ProductsByCategoryAdapter,
        items: List<CommonProductUiModel>
    ) {
        when (mode) {
            ProductListMode.HORIZONTAL -> {
                if (recyclerView.adapter !== horizontalAdapter) {
                    setupHorizontalProductsList(recyclerView, horizontalAdapter)
                }
                horizontalAdapter.submitList(items)
            }

            ProductListMode.GRID -> {
                if (recyclerView.adapter !== gridAdapter) {
                    setupGridProductsList(recyclerView, gridAdapter)
                }
                gridAdapter.submitList(items)
            }
        }
    }

    private fun setupHorizontalProductsList(
        recyclerView: RecyclerView,
        listAdapter: ProductHorizontalAdapter
    ) {
        recyclerView.apply {
            adapter = listAdapter
            setPadding(0, 0, 0, 0)
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            clearItemDecorations()
            addItemDecoration(
                HorizontalSpaceItemDecoration(
                    spaceWidth = requireContext().dpToPx(15)
                )
            )
        }
    }

    private fun setupGridProductsList(
        recyclerView: RecyclerView,
        listAdapter: ProductsByCategoryAdapter
    ) {
        recyclerView.apply {
            adapter = listAdapter
            setPadding(
                requireContext().dpToPx(15),
                0,
                requireContext().dpToPx(15),
                0
            )
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
            clearItemDecorations()
            addItemDecoration(
                GridSpacingItemDecoration(
                    spanCount = GRID_SPAN_COUNT,
                    spacing = requireContext().dpToPx(15),
                    includeEdge = false
                )
            )
        }
    }

    private fun RecyclerView.clearItemDecorations() {
        while (itemDecorationCount > 0) {
            removeItemDecorationAt(0)
        }
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

    private enum class ProductListMode {
        HORIZONTAL,
        GRID
    }

    private fun ProductListMode.toggle(): ProductListMode =
        if (this == ProductListMode.HORIZONTAL) ProductListMode.GRID else ProductListMode.HORIZONTAL

    private fun ProductListMode.actionText(): String =
        if (this == ProductListMode.HORIZONTAL) "See all" else "See less"

    private companion object {
        const val GRID_SPAN_COUNT = 2
    }
}
