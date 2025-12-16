package com.example.myshop

import Banner
import BannerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ShopFragment : Fragment(R.layout.fragment_shop) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vp = view.findViewById<ViewPager2>(R.id.vpBanners)


        val banners = listOf(
            Banner("Fresh Vegetables", "Get Up To 40% OFF"),
            Banner("Hot Deals", "Only Today"),
            Banner("Mega Sale", "Up to 70% OFF")
        )

        vp.adapter = BannerAdapter(banners)

        val productForExclusiveOffers = listOf(
            ProductForExclusiveOffer(
                "Red Apple",
                "1kg, Price/kg",
                "$4.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Organic Bananas",
                "7pcs, Price/ea",
                "$3.49",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Bell Pepper Red",
                "1kg, Price/kg",
                "$2.99",
                R.drawable.apple_picture
            ),

            ProductForExclusiveOffer(
                "Strawberries",
                "500g, Price/pack",
                "$5.49",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Blueberries",
                "250g, Price/pack",
                "$3.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Grapes Seedless",
                "1kg, Price/kg",
                "$4.29",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Mandarins",
                "1kg, Price/kg",
                "$2.79",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Avocado Hass",
                "2pcs, Price/ea",
                "$3.59",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Pineapple",
                "1pc, Price/ea",
                "$2.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer("Kiwi", "6pcs, Price/ea", "$3.19", R.drawable.apple_picture),
            ProductForExclusiveOffer("Mango", "1pc, Price/ea", "$2.49", R.drawable.apple_picture),
            ProductForExclusiveOffer(
                "Watermelon",
                "1pc, Price/ea",
                "$6.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Tomatoes Cherry",
                "400g, Price/pack",
                "$2.69",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Cucumber",
                "1pc, Price/ea",
                "$0.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer("Carrots", "1kg, Price/kg", "$1.39", R.drawable.apple_picture),
            ProductForExclusiveOffer(
                "Broccoli",
                "1pc, Price/ea",
                "$1.79",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Potatoes",
                "2kg, Price/bag",
                "$2.49",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer("Onions", "1kg, Price/kg", "$1.09", R.drawable.apple_picture),
            ProductForExclusiveOffer("Milk 2%", "1L, Price/ea", "$1.29", R.drawable.apple_picture),
            ProductForExclusiveOffer(
                "Greek Yogurt",
                "500g, Price/pack",
                "$2.99",
                R.drawable.apple_picture
            ),
            ProductForExclusiveOffer(
                "Cheddar Cheese",
                "200g, Price/pack",
                "$3.49",
                R.drawable.apple_picture
            )
        )


        val rvExclusiveOffer: RecyclerView = view.findViewById(R.id.rvExclusiveOffer)


        rvExclusiveOffer.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )



        rvExclusiveOffer.adapter = ExclusiveOfferAdapter(productForExclusiveOffers)

        val productForBestSelling = listOf(
            ProductForBestSelling("Red Apple", "1kg, Price/kg", "$4.99", R.drawable.apple_picture),
            ProductForBestSelling(
                "Organic Bananas",
                "7pcs, Price/ea",
                "$3.49",
                R.drawable.apple_picture
            ),
            ProductForBestSelling(
                "Bell Pepper Red",
                "1kg, Price/kg",
                "$2.99",
                R.drawable.apple_picture
            ),
            ProductForBestSelling(
                "Strawberries",
                "500g, Price/pack",
                "$5.49",
                R.drawable.apple_picture
            ),
            ProductForBestSelling(
                "Blueberries",
                "250g, Price/pack",
                "$3.99",
                R.drawable.apple_picture
            ),
            ProductForBestSelling(
                "Grapes Seedless",
                "1kg, Price/kg",
                "$4.29",
                R.drawable.apple_picture
            ),
            ProductForBestSelling("Mandarins", "1kg, Price/kg", "$2.79", R.drawable.apple_picture),
            ProductForBestSelling(
                "Avocado Hass",
                "2pcs, Price/ea",
                "$3.59",
                R.drawable.apple_picture
            ),
            ProductForBestSelling("Pineapple", "1pc, Price/ea", "$2.99", R.drawable.apple_picture),
            ProductForBestSelling("Kiwi", "6pcs, Price/ea", "$3.19", R.drawable.apple_picture),
            ProductForBestSelling("Mango", "1pc, Price/ea", "$2.49", R.drawable.apple_picture),
            ProductForBestSelling("Watermelon", "1pc, Price/ea", "$6.99", R.drawable.apple_picture),
            ProductForBestSelling(
                "Tomatoes Cherry",
                "400g, Price/pack",
                "$2.69",
                R.drawable.apple_picture
            ),
            ProductForBestSelling("Cucumber", "1pc, Price/ea", "$0.99", R.drawable.apple_picture),
            ProductForBestSelling("Carrots", "1kg, Price/kg", "$1.39", R.drawable.apple_picture),
            ProductForBestSelling("Broccoli", "1pc, Price/ea", "$1.79", R.drawable.apple_picture),
            ProductForBestSelling("Potatoes", "2kg, Price/bag", "$2.49", R.drawable.apple_picture),
            ProductForBestSelling("Onions", "1kg, Price/kg", "$1.09", R.drawable.apple_picture),
            ProductForBestSelling("Milk 2%", "1L, Price/ea", "$1.29", R.drawable.apple_picture),
            ProductForBestSelling(
                "Greek Yogurt",
                "500g, Price/pack",
                "$2.99",
                R.drawable.apple_picture
            ),
            ProductForBestSelling(
                "Cheddar Cheese",
                "200g, Price/pack",
                "$3.49",
                R.drawable.apple_picture
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

        val rvGroceriesProductsForCard: RecyclerView = view.findViewById(R.id.rvGroceriesProductCard)

        rvGroceriesProductsForCard.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvGroceriesProductsForCard.adapter = GroceriesProductCardAdapter(groceriesProductsForCardAdapter)
    }
}


