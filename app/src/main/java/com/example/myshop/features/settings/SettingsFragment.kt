package com.example.myshop.features.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.myshop.R
import com.example.myshop.core.ui.theme.MyShopTheme
import com.example.myshop.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSettingsBinding.bind(view)

        binding.settingsComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.settingsComposeView.setContent {
            MyShopTheme {
                SettingsScreen(
                    userName = "Test Test",
                    userEmail = "test@gmail.com",
                    onEditProfileClick = {
                        showComingSoon()
                    },
                    onMenuItemClick = {
                        showComingSoon()
                    },
                    onLogoutClick = {
                        showComingSoon()
                    }
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
