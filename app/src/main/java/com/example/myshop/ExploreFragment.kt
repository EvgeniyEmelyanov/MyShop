package com.example.myshop

import ExploreBannerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myshop.databinding.FragmentExploreBinding

class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    val exploreCategories = listOf(
        ExploreBanner(
            R.drawable.ic_fruits,
            "Fresh Fruits\n& Vegetable",
            R.color.bg_fruits,
            R.color.stroke_fruits
        ),
        ExploreBanner(
            R.drawable.ic_oil,
            "Cooking Oil\n& Ghee",
            R.color.bg_oil,
            R.color.stroke_oil
        ),
        ExploreBanner(
            R.drawable.ic_meat,
            "Meat & Fish",
            R.color.bg_meat,
            R.color.stroke_meat
        ),
        ExploreBanner(
            R.drawable.ic_bakery,
            "Bakery & Snacks",
            R.color.bg_bakery,
            R.color.stroke_bakery
        ),
        ExploreBanner(
            R.drawable.ic_dairy,
            "Dairy & Eggs",
            R.color.bg_dairy,
            R.color.stroke_dairy
        ),
        ExploreBanner(
            R.drawable.ic_beverages,
            "Beverages",
            R.color.bg_beverages,
            R.color.stroke_beverages
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExploreBinding.bind(view)

        binding.rvExploreBanner.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = ExploreBannerAdapter(exploreCategories)
            setHasFixedSize(true)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 2,
                        spacing = dpToPx(15),
                        includeEdge = false
                    )
                )
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dpToPx(dp: Int): Int =
        (dp * resources.displayMetrics.density).toInt()
}


