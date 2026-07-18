package com.example.myshop.features.orders.ui

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentOrdersBinding
import com.example.myshop.features.orders.presentation.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrdersFragment : Fragment(R.layout.fragment_orders) {
    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    private val vm: OrdersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrdersBinding.bind(view)

        binding.ordersComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.ordersComposeView.setContent {
            MyShopTheme {
                val state = vm.state.collectAsStateWithLifecycle()

                OrderScreen(
                    state = state.value,
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
