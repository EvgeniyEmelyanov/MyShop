package com.example.myshop.features.shop.ui

import com.example.myshop.features.shop.model.Banner
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.features.shop.model.GroceriesCategory
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.data.product.datasource.ProductStore
import com.example.myshop.databinding.FragmentShopBinding
import com.example.myshop.di.AppGraph
import com.example.myshop.domain.cart.model.Amount
import com.example.myshop.domain.product.model.AmountType
import com.example.myshop.features.shop.model.ShopViewModel
import com.example.myshop.features.shop.model.ShopViewModelFactory

class ShopFragment : BaseFragment(R.layout.fragment_shop) {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val vm: ShopViewModel by viewModels { ShopViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopBinding.bind(view)

        val ivCarrot = binding.ivCarrot as ImageView
        setInsetsForView(ivCarrot, additionalTopMarginDp = 10)


        val vp = view.findViewById<ViewPager2>(R.id.vpBanners)

        val banners = listOf(
            Banner("Fresh Vegetables", "Get Up To 40% OFF"),
            Banner("Hot Deals", "Only Today"),
            Banner("Mega Sale", "Up to 70% OFF")
        )

        vp.adapter = BannerAdapter(banners)



        binding.rvExclusiveOffer.apply {
            adapter = ProductHorizontalAdapter(
                ProductStore.exclusiveOffers(),

                onRootClick = { productId ->
                    findNavController().navigate(
                        R.id.action_shopFragment_to_productDetailFragment,
                        bundleOf("productId" to productId)
                    )

                },
                onAddBtnClick = { productId ->
                    val p = AppGraph.getProductByIdUseCase.getById(productId) ?: return@ProductHorizontalAdapter
                    val start = when (p.amountType) {
                        AmountType.PIECE -> Amount.Piece(1)
                        AmountType.WEIGHT -> Amount.Grams(1000)
                    }
                    AppGraph.addProductToCartUseCase.addProduct(productId, start)
                }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )


            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15),
                    )
                )
            }
        }

        binding.rvBestSellingProducts.apply {
            adapter = ProductHorizontalAdapter(
                ProductStore.bestSelling(),

                onRootClick = { productId ->
                    findNavController().navigate(
                        R.id.action_shopFragment_to_productDetailFragment,
                        bundleOf("productId" to productId)
                    )

                },
                onAddBtnClick = { productId ->
                    val p = AppGraph.getProductByIdUseCase.getById(productId) ?: return@ProductHorizontalAdapter
                    val start = when (p.amountType) {
                        AmountType.PIECE -> Amount.Piece(1)
                        AmountType.WEIGHT -> Amount.Grams(1000)
                    }
                    AppGraph.addProductToCartUseCase.addProduct(productId, start)
                }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15),
                    )
                )
            }
        }

        binding.rvGroceriesProductCard.apply {
            adapter = ProductHorizontalAdapter(
                ProductStore.groceriesProduct(),

                onRootClick = { productId ->
                    findNavController().navigate(
                        R.id.action_shopFragment_to_productDetailFragment,
                        bundleOf("productId" to productId)
                    )

                },
                onAddBtnClick = {
                    productId -> val p = AppGraph.getProductByIdUseCase.getById(productId) ?: return@ProductHorizontalAdapter
                    val start = when (p.amountType) {
                        AmountType.PIECE -> Amount.Piece(1)
                        AmountType.WEIGHT -> Amount.Grams(1000)
                    }
                    AppGraph.addProductToCartUseCase.addProduct(productId, start) }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    HorizontalSpaceItemDecoration(
                        spaceWidth = requireContext().dpToPx(15),
                    )
                )
            }
        }


        val groceries = listOf(
            GroceriesCategory(
                title = "Pulses",
                imageRes = R.drawable.pulses_picture,
                backgroundColorRes = R.color.bg_grocery_pulses
            ),
            GroceriesCategory(
                title = "Rice",
                imageRes = R.drawable.rice_pictute,
                backgroundColorRes = R.color.bg_grocery_rice
            ),
            GroceriesCategory(
                title = "Meat",
                imageRes = R.drawable.rice_pictute,
                backgroundColorRes = R.color.bg_grocery_meat
            )
        )

        val rvGroceries: RecyclerView = view.findViewById(R.id.rvGroceries)

        rvGroceries.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvGroceries.adapter = GroceriesAdapter(groceries)


    }
}