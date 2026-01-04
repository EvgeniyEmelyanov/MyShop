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
import com.example.myshop.ProductStore.productForExclusiveOffers
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
            adapter = ExclusiveOfferAdapter(productForExclusiveOffers) {
                productId -> findNavController().navigate(
                    R.id.action_shopFragment_to_productDetailFragment,
                bundleOf("productId" to productId)
                )
            }
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }





        val productForBestSelling = listOf(
            ProductForBestSelling(
                id = "apple_red",
                title = "Red Apple",
                weight = "1kg, Price/kg",
                price = "$4.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "banana_organic",
                title = "Organic Bananas",
                weight = "7pcs, Price/ea",
                price = "$3.49",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "pepper_red",
                title = "Bell Pepper Red",
                weight = "1kg, Price/kg",
                price = "$2.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "strawberries",
                title = "Strawberries",
                weight = "500g, Price/pack",
                price = "$5.49",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "blueberries",
                title = "Blueberries",
                weight = "250g, Price/pack",
                price = "$3.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "grapes_seedless",
                title = "Grapes Seedless",
                weight = "1kg, Price/kg",
                price = "$4.29",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "mandarins",
                title = "Mandarins",
                weight = "1kg, Price/kg",
                price = "$2.79",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "avocado_hass",
                title = "Avocado Hass",
                weight = "2pcs, Price/ea",
                price = "$3.59",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "pineapple",
                title = "Pineapple",
                weight = "1pc, Price/ea",
                price = "$2.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "kiwi",
                title = "Kiwi",
                weight = "6pcs, Price/ea",
                price = "$3.19",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "mango",
                title = "Mango",
                weight = "1pc, Price/ea",
                price = "$2.49",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "watermelon",
                title = "Watermelon",
                weight = "1pc, Price/ea",
                price = "$6.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "tomatoes_cherry",
                title = "Tomatoes Cherry",
                weight = "400g, Price/pack",
                price = "$2.69",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "cucumber",
                title = "Cucumber",
                weight = "1pc, Price/ea",
                price = "$0.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "carrots",
                title = "Carrots",
                weight = "1kg, Price/kg",
                price = "$1.39",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "broccoli",
                title = "Broccoli",
                weight = "1pc, Price/ea",
                price = "$1.79",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "potatoes",
                title = "Potatoes",
                weight = "2kg, Price/bag",
                price = "$2.49",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "onions",
                title = "Onions",
                weight = "1kg, Price/kg",
                price = "$1.09",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "milk_2_percent",
                title = "Milk 2%",
                weight = "1L, Price/ea",
                price = "$1.29",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "yogurt_greek",
                title = "Greek Yogurt",
                weight = "500g, Price/pack",
                price = "$2.99",
                imageRes = R.drawable.apple_picture
            ),
            ProductForBestSelling(
                id = "cheddar_cheese",
                title = "Cheddar Cheese",
                weight = "200g, Price/pack",
                price = "$3.49",
                imageRes = R.drawable.apple_picture
            )
        )


        val bestSellingProducts: RecyclerView = view.findViewById(R.id.bestSellingProducts)

        bestSellingProducts.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        bestSellingProducts.adapter = BestSellingAdapter(productForBestSelling)


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


        val groceriesProductsForCardAdapter = listOf(
            GroceriesProductCard
                ("Red Apple", "1kg, Price/kg", "$4.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Organic Bananas", "7pcs, Price/ea", "$3.49", R.drawable.apple_picture),
            GroceriesProductCard
                ("Bell Pepper Red", "1kg, Price/kg", "$2.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Strawberries", "500g, Price/pack", "$5.49", R.drawable.apple_picture),
            GroceriesProductCard
                ("Blueberries", "250g, Price/pack", "$3.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Grapes Seedless", "1kg, Price/kg", "$4.29", R.drawable.apple_picture),
            GroceriesProductCard
                ("Mandarins", "1kg, Price/kg", "$2.79", R.drawable.apple_picture),
            GroceriesProductCard
                ("Avocado Hass", "2pcs, Price/ea", "$3.59", R.drawable.apple_picture),
            GroceriesProductCard
                ("Pineapple", "1pc, Price/ea", "$2.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Kiwi", "6pcs, Price/ea", "$3.19", R.drawable.apple_picture),
            GroceriesProductCard
                ("Mango", "1pc, Price/ea", "$2.49", R.drawable.apple_picture),
            GroceriesProductCard
                ("Watermelon", "1pc, Price/ea", "$6.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Tomatoes Cherry", "400g, Price/pack", "$2.69", R.drawable.apple_picture),
            GroceriesProductCard
                ("Cucumber", "1pc, Price/ea", "$0.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Carrots", "1kg, Price/kg", "$1.39", R.drawable.apple_picture),
            GroceriesProductCard
                ("Broccoli", "1pc, Price/ea", "$1.79", R.drawable.apple_picture),
            GroceriesProductCard
                ("Potatoes", "2kg, Price/bag", "$2.49", R.drawable.apple_picture),
            GroceriesProductCard
                ("Onions", "1kg, Price/kg", "$1.09", R.drawable.apple_picture),
            GroceriesProductCard
                ("Milk 2%", "1L, Price/ea", "$1.29", R.drawable.apple_picture),
            GroceriesProductCard
                ("Greek Yogurt", "500g, Price/pack", "$2.99", R.drawable.apple_picture),
            GroceriesProductCard
                ("Cheddar Cheese", "200g, Price/pack", "$3.49", R.drawable.apple_picture)
        )

        val rvGroceriesProductsForCard: RecyclerView =
            view.findViewById(R.id.rvGroceriesProductCard)

        rvGroceriesProductsForCard.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvGroceriesProductsForCard.adapter =
            GroceriesProductCardAdapter(groceriesProductsForCardAdapter)

//        findNavController().navigate(
//            R.id.action_shopFragment_to_productDetailFragment,
//            bundleOf("productId" to "apple_red")
//        )


    }
}


