package com.example.myshop.features.settings.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentSettingsBinding
import com.example.myshop.features.settings.presentation.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val vm: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)

        binding.settingsComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.settingsComposeView.setContent {
            val state by vm.state.collectAsStateWithLifecycle()

            MyShopTheme {
                SettingsScreen(
                    userName = state.userProfile.userName,
                    userEmail = state.userProfile.userEmail,
                    onMenuItemClick = {
                        when (it) {
                            SettingsMenuAction.Orders -> openOrders()
                            SettingsMenuAction.MyDetails -> showComingSoon()
                            SettingsMenuAction.DeliveryAddress -> showComingSoon()
                            SettingsMenuAction.PaymentMethods -> showComingSoon()
                            SettingsMenuAction.PromoCode -> showComingSoon()
                            SettingsMenuAction.Notifications -> showComingSoon()
                            SettingsMenuAction.Help -> showComingSoon()
                            SettingsMenuAction.About -> showComingSoon()

                        }
                    },
                    onLogoutClick = {
                        showComingSoon()
                    },
                    onSaveProfileClick = { name, email ->
                        vm.saveUserProfile(
                            userName = name,
                            userEmail = email
                        )
                    }
                )
            }
        }
    }

    private fun showComingSoon() {
        Toast.makeText(requireContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show()
    }

    private fun openOrders() {
        findNavController().navigate(R.id.action_accountFragment_to_ordersFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
