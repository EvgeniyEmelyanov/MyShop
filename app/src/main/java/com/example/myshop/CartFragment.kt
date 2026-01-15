package com.example.myshop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.databinding.FragmentCartBinding


class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    private val vm: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        cartAdapter = CartAdapter(
            items = emptyList(),
            onClickIncrease = { id -> vm.onIncrease(id) },
            onClickDecrease = { id -> vm.onDecrease(id) },
            onClickDelete = { id -> vm.onDelete(id) }
        )

        binding.rvProductsCart.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())

            if (itemDecorationCount == 0) {
                addItemDecoration(
                    CartDividerDecoration(
                        context = requireContext(),
                        colorRes = R.color.line_for_products_banner,
                        heightPx = requireContext().dpToPx(1),
                        insetPx = requireContext().dpToPx(25),
                        skipLast = true
                    )
                )
            }
        }

        vm.state.observe(viewLifecycleOwner, Observer { state ->
            cartAdapter.submitList(state.items)
            binding.tvCheckoutTotal.text = state.totalString
        })

        vm.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}







