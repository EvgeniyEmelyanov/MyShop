package com.example.myshop

import ExploreBannerAdapter
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.databinding.FragmentExploreBinding

class ExploreFragment : BaseFragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val exploreCategories = listOf(
        ExploreBanner(
            R.drawable.ic_fruits,
            "Fresh Fruits\n& Vegetable",
            R.color.bg_fruits,
            R.color.stroke_fruits,
            Category.FRUITS_VEGETABLES
        ),
        ExploreBanner(
            R.drawable.ic_oil,
            "Cooking Oil\n& Ghee",
            R.color.bg_oil,
            R.color.stroke_oil,
            Category.OIL_GHEE
        ),
        ExploreBanner(
            R.drawable.ic_meat,
            "Meat & Fish",
            R.color.bg_meat,
            R.color.stroke_meat,
            Category.MEAT_FISH
        ),
        ExploreBanner(
            R.drawable.ic_bakery,
            "Bakery & Snacks",
            R.color.bg_bakery,
            R.color.stroke_bakery,
            Category.BAKERY_SNACKS
        ),
        ExploreBanner(
            R.drawable.ic_dairy,
            "Dairy & Eggs",
            R.color.bg_dairy,
            R.color.stroke_dairy,
            Category.DAIRY_EGGS
        ),
        ExploreBanner(
            R.drawable.ic_beverages,
            "Beverages",
            R.color.bg_beverages,
            R.color.stroke_beverages,
            Category.BEVERAGES
        )
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)

        setInsetsForView(binding.tvHeaderExplore, additionalTopMarginDp = 10)

        val adapter = ExploreBannerAdapter(
            items = exploreCategories,
            onClick = { banner ->
                findNavController().navigate(
                    R.id.action_exploreFragment_to_productsByCategoryFragment,
                    bundleOf("category" to banner.category.name)
                )
            }
        )

        binding.rvExploreBanner.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
            setHasFixedSize(true)

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    GridSpacingItemDecoration(
                        spanCount = 2,
                        spacing = requireContext().dpToPx(15),
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
}
