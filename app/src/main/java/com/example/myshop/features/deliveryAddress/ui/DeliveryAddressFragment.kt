package com.example.myshop.features.deliveryAddress.ui

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentDeliveryAddressBinding
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressViewModel
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressEditorViewModel.Companion.ADDRESS_ID_ARGUMENT
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressEditorViewModel.Companion.NEW_ADDRESS_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryAddressFragment : Fragment(R.layout.fragment_delivery_address) {

    private var _binding: FragmentDeliveryAddressBinding? = null
    private val binding get() = _binding!!
    private val vm: DeliveryAddressViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDeliveryAddressBinding.bind(view)

        binding.deliveryAddressComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.deliveryAddressComposeView.setContent {
            val state by vm.state.collectAsStateWithLifecycle()

            MyShopTheme {
                DeliveryAddressScreen(
                    state = state,
                    onCreateClick = { openAddressEditor(NEW_ADDRESS_ID) },
                    onEditClick = ::openAddressEditor,
                    onRemoveClick = vm::deleteAddress,
                    onSetDefaultClick = vm::setDefaultAddress,
                    onBackClick = { findNavController().popBackStack() }
                )
            }
        }
    }

    private fun openAddressEditor(addressId: Long) {
        val arguments = Bundle().apply {
            putLong(ADDRESS_ID_ARGUMENT, addressId)
        }

        findNavController().navigate(
            R.id.action_deliveryAddressFragment_to_deliveryAddressEditorFragment,
            arguments
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
