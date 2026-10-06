package com.example.myshop.features.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myshop.R
import com.example.myshop.databinding.BottomSheetCheckoutBinding
import com.example.myshop.domain.deliveryAddress.DeliveryAddress
import com.example.myshop.domain.deliveryAddress.DeliveryAddressType
import com.example.myshop.domain.order.model.FulfillmentSelection
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutBottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_checkout) {

    private var _binding: BottomSheetCheckoutBinding? = null
    private val binding get() = _binding!!
    private val vm: CheckoutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = BottomSheetCheckoutBinding.bind(view)

        val totalString = requireArguments().getString(ARG_TOTAL_STRING).orEmpty()
        binding.tvTotalCost.text = totalString

        binding.btnCloseCheckout.setOnClickListener {
            dismiss()
        }

        binding.deliveryClickTarget.setOnClickListener {
            val state = vm.state.value
            if (state.hasError) vm.retry()
            else if (!state.isLoading) showDeliveryChoices(state)
        }

        binding.btnPlaceOrder.setOnClickListener {
            val selection = vm.state.value.selection ?: return@setOnClickListener
            binding.btnPlaceOrder.isEnabled = false
            setFragmentResult(
                CHECKOUT_RESULT_KEY,
                Bundle().apply {
                    putBoolean(CHECKOUT_CONFIRMED_KEY, true)
                    when (selection) {
                        FulfillmentSelection.Pickup -> putString(CHECKOUT_FULFILLMENT_KEY, FULFILLMENT_PICKUP)
                        is FulfillmentSelection.Delivery -> {
                            putString(CHECKOUT_FULFILLMENT_KEY, FULFILLMENT_DELIVERY)
                            putLong(CHECKOUT_ADDRESS_ID_KEY, selection.addressId)
                        }
                    }
                }
            )
            dismiss()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.state.collect(::render)
            }
        }
    }

    private fun render(state: CheckoutUiState) {
        binding.btnPlaceOrder.isEnabled = !state.isLoading && !state.hasError && state.selection != null
        binding.tvDeliveryValue.text = when {
            state.isLoading -> getString(R.string.checkout_addresses_loading)
            state.hasError -> getString(R.string.checkout_addresses_error)
            state.selection == FulfillmentSelection.Pickup -> getString(R.string.checkout_pickup)
            state.selection is FulfillmentSelection.Delivery -> {
                state.addresses.firstOrNull { it.id == state.selection.addressId }
                    ?.let(::addressLabel) ?: getString(R.string.checkout_delivery_method)
            }
            else -> getString(R.string.checkout_delivery_method)
        }
    }

    private fun showDeliveryChoices(state: CheckoutUiState) {
        val choices = listOf(getString(R.string.checkout_pickup)) + state.addresses.map(::addressLabel)
        val selectedIndex = when (val selection = state.selection) {
            FulfillmentSelection.Pickup -> 0
            is FulfillmentSelection.Delivery -> state.addresses.indexOfFirst {
                it.id == selection.addressId
            } + 1
            null -> -1
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.checkout_choose_delivery)
            .setSingleChoiceItems(choices.toTypedArray(), selectedIndex) { dialog, index ->
                vm.select(
                    if (index == 0) FulfillmentSelection.Pickup
                    else FulfillmentSelection.Delivery(state.addresses[index - 1].id)
                )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    private fun addressLabel(address: DeliveryAddress): String {
        val typeRes = when (address.type) {
            DeliveryAddressType.HOME -> R.string.address_type_home
            DeliveryAddressType.WORK -> R.string.address_type_work
            DeliveryAddressType.OTHER -> R.string.address_type_other
        }
        return getString(
            R.string.checkout_address_option,
            getString(typeRes), address.settlement, address.street, address.house
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CheckoutBottomSheetFragment"
        const val CHECKOUT_RESULT_KEY = "checkout_result"
        const val CHECKOUT_CONFIRMED_KEY = "checkout_confirmed"
        const val CHECKOUT_FULFILLMENT_KEY = "checkout_fulfillment"
        const val CHECKOUT_ADDRESS_ID_KEY = "checkout_address_id"
        const val FULFILLMENT_PICKUP = "PICKUP"
        const val FULFILLMENT_DELIVERY = "DELIVERY"
        private const val ARG_TOTAL_STRING = "arg_total_string"

        fun newInstance(totalString: String): CheckoutBottomSheetFragment {
            return CheckoutBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TOTAL_STRING, totalString)
                }
            }
        }
    }
}
