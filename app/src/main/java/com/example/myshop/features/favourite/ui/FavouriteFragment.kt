package com.example.myshop.features.favourite.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.core.ui.dpToPx
import com.example.myshop.features.favourite.presentation.FavouriteViewModel
import com.example.myshop.R
import com.example.myshop.app.BaseFragment
import com.example.myshop.core.ui.ContentState
import com.example.myshop.databinding.FragmentFavouriteBinding
import com.example.myshop.features.favourite.presentation.FavouriteUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : BaseFragment(R.layout.fragment_favourite) {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val vm: FavouriteViewModel by viewModels ()
    private lateinit var favouriteAdapter: FavouriteAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouriteBinding.bind(view)

        setInsetsForFragment(binding.tvHeaderFragment, additionalTopMarginDp = 10)

        setupAdapter()

        setupList()

        observeState()

        binding.btnAddAllToCart.setOnClickListener {
            vm.onAddAllToCart()

        }

        binding.stateView.btnRetry.setOnClickListener {
            vm.load()
        }

    }

    private fun setupAdapter() {
        favouriteAdapter = FavouriteAdapter(
            onClickItem = { id -> openProductDetail(id) }
        )

    }


    private fun setupList() {
        binding.rvProductsFavourite.apply {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    FavouriteDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.divider,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(15),
                        skipLast = true
                    )
                )
            }
        }

    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        vm.toastMessage.observe(viewLifecycleOwner) {message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                vm.toastShown()
            }
        }

    }

    private fun render(state: FavouriteUiState) {
        favouriteAdapter.submitList(state.items)

        binding.progressBar.visibility =
            if (state.contentState == ContentState.LOADING) View.VISIBLE else View.GONE

        binding.rvProductsFavourite.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE

        binding.stateView.root.visibility =
            if (state.contentState == ContentState.EMPTY ||
                state.contentState == ContentState.ERROR
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.stateView.tvStateMessage.setText(
            if (state.contentState == ContentState.ERROR) {
                R.string.error_loading_products
            } else {
                R.string.add_product_to_favourite
            }
        )

        binding.stateView.btnRetry.visibility =
            if (state.contentState == ContentState.ERROR) View.VISIBLE else View.GONE

        binding.btnAddAllToCart.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE



    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            R.id.action_favouriteFragment_to_productDetailFragment,
            bundleOf("productId" to productId)
        )
    }

    override fun onResume() {
        super.onResume()
        vm.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
