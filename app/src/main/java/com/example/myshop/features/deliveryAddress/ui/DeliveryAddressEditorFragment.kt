package com.example.myshop.features.deliveryAddress.ui

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentDeliveryAddressEditorBinding
import com.example.myshop.features.deliveryAddress.presentation.DeliveryAddressEditorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DeliveryAddressEditorFragment : Fragment(R.layout.fragment_delivery_address_editor) {

    private var _binding: FragmentDeliveryAddressEditorBinding? = null
    private val binding get() = _binding!!
    private val vm: DeliveryAddressEditorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDeliveryAddressEditorBinding.bind(view)

        binding.deliveryAddressEditorComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.deliveryAddressEditorComposeView.setContent {
            val state by vm.state.collectAsStateWithLifecycle()

            MyShopTheme {
                DeliveryAddressEditorScreen(
                    state = state,
                    onBackClick = { findNavController().popBackStack() },
                    onTypeChanged = vm::onTypeChanged,
                    onSettlementChanged = vm::onSettlementChanged,
                    onStreetChanged = vm::onStreetChanged,
                    onHouseChanged = vm::onHouseChanged,
                    onBuildingChanged = vm::onBuildingChanged,
                    onApartmentChanged = vm::onApartmentChanged,
                    onSaveClick = vm::onSaveClick,
                    onRetryClick = vm::retryLoading
                )
            }
        }

        observeSaveEvent()
    }

    private fun observeSaveEvent() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.addressSavedEvent.collect {
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
