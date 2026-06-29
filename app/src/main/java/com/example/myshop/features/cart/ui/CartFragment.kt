package com.example.myshop.features.cart.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myshop.app.BaseFragment
import com.example.myshop.R
import com.example.myshop.core.ui.ContentState
import com.example.myshop.core.ui.dpToPx
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.features.cart.presentation.CartUiState
import com.example.myshop.features.cart.presentation.CartViewModel
import com.example.myshop.features.checkout.CheckoutBottomSheetFragment
import com.example.myshop.features.checkout.CheckoutBottomSheetFragment.Companion.CHECKOUT_CONFIRMED_KEY
import com.example.myshop.features.checkout.CheckoutBottomSheetFragment.Companion.CHECKOUT_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        setupCheckoutResultListener()


        vm.load()
    }

    private fun setClick() {
        binding.stateView.btnRetry.setOnClickListener {
            vm.load()
        }

        binding.checkoutBar.setOnClickListener {
            val state = vm.state.value

            if (state.contentState == ContentState.CONTENT && parentFragmentManager.findFragmentByTag(
                    CheckoutBottomSheetFragment.TAG
                ) == null
            ) {
                CheckoutBottomSheetFragment.newInstance(totalString = state.totalString)
                    .show(parentFragmentManager, CheckoutBottomSheetFragment.TAG)
            }
        }
    }

    private fun setupCheckoutResultListener() {
        setFragmentResultListener(CHECKOUT_RESULT_KEY) { _, bundle ->
            val isConfirmed = bundle.getBoolean(CHECKOUT_CONFIRMED_KEY)

            if (isConfirmed) {
                vm.placeOrder()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    vm.state.collect { state ->
                        render(state)
                    }
                }

                launch {
                    vm.orderPlacedEvent.collect {
                        openOrderAcceptedScreen()
                    }
                }
            }
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

    private fun openOrderAcceptedScreen() {
        findNavController().navigate(
            R.id.action_cartFragment_to_orderAcceptedFragment
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
