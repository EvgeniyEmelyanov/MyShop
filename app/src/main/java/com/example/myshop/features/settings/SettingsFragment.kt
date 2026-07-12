package com.example.myshop.features.settings

import android.os.*
import android.view.*
import android.widget.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.*
import androidx.lifecycle.compose.*
import com.example.myshop.R
import com.example.myshop.core.ui.theme.*
import com.example.myshop.databinding.*
import dagger.hilt.android.*

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
                        onEditProfileClick = {
                            showComingSoon()
                        },
                        onMenuItemClick = {
                            showComingSoon()
                        },
                        onLogoutClick = {
                            showComingSoon()
                        },
                        onSaveProfileClick = { name, email ->
                            vm.saveUserProfile(
                                userName = name,
                                userEmail = email
                            )
                        },
                    )
                }
        }
    }

    private fun showComingSoon() {
        Toast.makeText(requireContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
