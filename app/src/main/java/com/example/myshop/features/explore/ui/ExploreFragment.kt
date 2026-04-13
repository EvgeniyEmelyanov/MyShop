package com.example.myshop.features.explore.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.decoration.GridSpacingItemDecoration
import com.example.myshop.databinding.FragmentExploreBinding
import com.example.myshop.domain.product.model.Category
import com.example.myshop.features.explore.presentation.ExploreUiState
import com.example.myshop.features.explore.presentation.ExploreViewModel

class ExploreFragment : BaseFragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    private val vm: ExploreViewModel by viewModels()
    private lateinit var exploreCategoriesAdapter: ExploreBannerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentExploreBinding.bind(view)

        setInsetsForView(binding.tvHeaderExplore, additionalTopMarginDp = 10)

        setupAdapter()

        setupList()

        observeState()
    }

    private fun setupAdapter() {
        exploreCategoriesAdapter = ExploreBannerAdapter(
            onClick = { banner ->
                openProductsByCategory(banner.category)
            }
        )

    }

    private fun setupList() {
        binding.rvExploreBanner.apply {
            adapter = exploreCategoriesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
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

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: ExploreUiState) {
        exploreCategoriesAdapter.submitList(state.categories)
    }

    private fun openProductsByCategory(category: Category) {
        findNavController().navigate(
            R.id.action_exploreFragment_to_productsByCategoryFragment,
            bundleOf("category" to category.name)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}