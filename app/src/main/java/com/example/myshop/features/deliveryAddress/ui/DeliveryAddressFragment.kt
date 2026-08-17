package com.example.myshop.features.deliveryAddress.ui

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentDeliveryAddressBinding

class DeliveryAddressFragment : Fragment(R.layout.fragment_delivery_address) {

    private var _binding: FragmentDeliveryAddressBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDeliveryAddressBinding.bind(view)

        binding.deliveryAddressComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.deliveryAddressComposeView.setContent {
            MyShopTheme {
                DeliveryAddressScreen(
                    onCreateClick = {},
                    onBackClick = { findNavController().popBackStack() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
