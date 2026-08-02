package com.example.myshop.features.myDetails.ui

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
import com.example.myshop.databinding.FragmentMyDetailsBinding
import com.example.myshop.features.myDetails.presentation.MyDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDetailsFragment : Fragment(R.layout.fragment_my_details) {

    private var _binding: FragmentMyDetailsBinding? = null
    private val binding get() = _binding!!
    private val vm: MyDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMyDetailsBinding.bind(view)

        binding.myDetailsComposeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        binding.myDetailsComposeView.setContent {
            val state by vm.state.collectAsStateWithLifecycle()

            MyShopTheme {
                DetailsScreen(
                    state = state,
                    onEditClick = vm::onEditClick,
                    onBackClick = ::backToSettingsScreen,
                    onCancelClick = vm::onCancelClick,
                    onSaveClick = vm::onSaveClick,
                    onNameChanged = vm::onNameChanged,
                    onEmailChanged = vm::onEmailChanged,
                    onPhoneChanged = vm::onPhoneChanged,
                    onDateChanged = vm::onDateChanged,
                    onGenderChanged = vm::onGenderChanged
                )
            }
        }
    }

    private fun backToSettingsScreen() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
