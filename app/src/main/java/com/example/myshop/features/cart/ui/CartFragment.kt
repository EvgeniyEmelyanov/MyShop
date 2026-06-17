package com.example.myshop.features.cart.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.evgeniyemelyanov.core.ui.dpToPx
import com.example.myshop.app.BaseFragment
import com.example.myshop.R
import com.example.myshop.core.ui.ContentState
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.features.cart.presentation.CartUiState
import com.example.myshop.features.cart.presentation.CartViewModel
import com.example.myshop.features.checkout.CheckoutBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val vm: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        setInsetsForFragment(binding.tvHeaderCart, additionalTopMarginDp = 10)

        setupAdapter()
        setupList()
        observeState()
        setClick()

        vm.load()
    }

    private fun setClick() {
        binding.stateView.btnRetry.setOnClickListener {
            vm.load()
        }

        binding.checkoutBar.setOnClickListener {
            val state = vm.state.value ?: return@setOnClickListener

            if (state.contentState == ContentState.CONTENT && parentFragmentManager.findFragmentByTag(
                    CheckoutBottomSheetFragment.TAG
                ) == null
            ) {
                CheckoutBottomSheetFragment.newInstance(totalString = state.totalString)
                    .show(parentFragmentManager, CheckoutBottomSheetFragment.TAG)
            }
        }
    }

    private fun setupAdapter() {
        cartAdapter = CartAdapter(
            onClickIncrease = { id -> vm.increaseAmount(id) },
            onClickDecrease = { id -> vm.decreaseAmount(id) }) { id -> vm.removeProduct(id) }
    }

    private fun setupList() {
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
    }

    private fun observeState() {
        vm.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: CartUiState) {
        cartAdapter.submitList(state.items)
        binding.tvCheckoutTotal.text = state.totalString

        binding.progressBar.visibility =
            if (state.contentState == ContentState.LOADING) View.VISIBLE else View.GONE

        binding.rvProductsCart.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE

        binding.stateView.root.visibility =
            if (state.contentState == ContentState.EMPTY || state.contentState == ContentState.ERROR) {
                View.VISIBLE
            } else {
                View.GONE
            }

        binding.stateView.tvStateMessage.setText(
            if (state.contentState == ContentState.ERROR) {
                R.string.error_loading_cart
            } else {
                R.string.empty_cart
            }
        )

        binding.stateView.btnRetry.visibility =
            if (state.contentState == ContentState.ERROR) View.VISIBLE else View.GONE

        binding.checkoutBar.visibility =
            if (state.contentState == ContentState.CONTENT) View.VISIBLE else View.GONE


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
