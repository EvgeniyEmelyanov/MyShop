package com.example.myshop.features.cart.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.app.BaseFragment
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.features.cart.presentation.CartViewModelFactory
import com.example.myshop.features.cart.presentation.CartViewModel

class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    private val vm: CartViewModel by viewModels { CartViewModelFactory() }
    private lateinit var cartAdapter: CartAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        setInsetsForFragment(binding.tvHeaderCart, additionalTopMarginDp = 10)


        cartAdapter = CartAdapter(
            items = emptyList(),
            onClickIncrease = { id -> vm.increaseAmount(id) },
            onClickDecrease = { id -> vm.decreaseAmount(id) },
            onClickDelete = { id -> vm.removeProduct(id) }
        )

        binding.rvProductsCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CartDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.divider,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(25),
                        skipLast = true
                    )
                )
            }
        }

        vm.state.observe(viewLifecycleOwner) { state ->
            cartAdapter.submitList(state.items)
            binding.tvCheckoutTotal.text = state.totalString
        }

        vm.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}