package com.example.myshop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!

    private val vm: FavouriteViewModel by viewModels()
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouriteBinding.bind(view)

        favouriteAdapter = FavouriteAdapter(
            items = emptyList(),
            onClickBtnArrow = { id ->
                val args = Bundle().apply { putString("productId", id) }
                findNavController().navigate(
                    R.id.action_favouriteFragment_to_productDetailFragment,
                    args
                )
            }
        )

        binding.rvProductsCart.apply {
            adapter = favouriteAdapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    FavouriteDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.line_for_products_banner,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(25),
                        skipLast = true
                    )
                )
            }
        }

        vm.state.observe(viewLifecycleOwner) { state ->
            favouriteAdapter.submitList(state.items)
        }

        vm.load()
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
