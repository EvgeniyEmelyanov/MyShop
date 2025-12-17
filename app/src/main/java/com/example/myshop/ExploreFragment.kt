package com.example.myshop

import ExploreBannerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExploreFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exploreCategories = listOf(
            ExploreBanner(
                image = R.drawable.ic_fruits,
                title = "Fresh Fruits\n& Vegetable",
                backgroundColorRes = R.color.bg_fruits,
                strokeColorRes = R.color.stroke_fruits
            ),
            ExploreBanner(
                image = R.drawable.ic_oil,
                title = "Cooking Oil\n& Ghee",
                backgroundColorRes = R.color.bg_oil,
                strokeColorRes = R.color.stroke_oil
            ),
            ExploreBanner(
                image = R.drawable.ic_meat,
                title = "Meat & Fish",
                backgroundColorRes = R.color.bg_meat,
                strokeColorRes = R.color.stroke_meat
            ),
            ExploreBanner(
                image = R.drawable.ic_bakery,
                title = "Bakery & Snacks",
                backgroundColorRes = R.color.bg_bakery,
                strokeColorRes = R.color.stroke_bakery
            ),
            ExploreBanner(
                image = R.drawable.ic_dairy,
                title = "Dairy & Eggs",
                backgroundColorRes = R.color.bg_dairy,
                strokeColorRes = R.color.stroke_dairy
            ),
            ExploreBanner(
                image = R.drawable.ic_beverages,
                title = "Beverages",
                backgroundColorRes = R.color.bg_beverages,
                strokeColorRes = R.color.stroke_beverages
            )
        )

        val rvExploreBanner: RecyclerView = view.findViewById(R.id.rvExploreBanner)
        rvExploreBanner.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        rvExploreBanner.adapter = ExploreBannerAdapter(exploreCategories)





    }

}
