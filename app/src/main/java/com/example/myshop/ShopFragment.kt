package com.example.myshop

import Banner
import BannerAdapter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myshop.databinding.FragmentShopBinding

class ShopFragment : Fragment(R.layout.fragment_shop) {
    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentShopBinding.bind(view)

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
                    AppState.cartManager.addToCart(productId)
                }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
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
                    AppState.cartManager.addToCart(productId)
                }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
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
                onAddBtnClick = { productId -> AppState.cartManager.addToCart(productId) }
            )

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
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

