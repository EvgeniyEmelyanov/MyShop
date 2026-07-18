package com.example.myshop.features.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.user.UserProfile
import com.example.myshop.domain.user.usecase.ObserveUserProfileUseCase
import com.example.myshop.domain.user.usecase.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        observeUserProfile()
    }

    fun saveUserProfile(userName: String, userEmail: String) {
        viewModelScope.launch {
            saveUserProfileUseCase(
                UserProfile(
                    fullName = userName,
                    email = userEmail,
                    avatarUri = _state.value.userProfile.userAvatarUri
                )
            )
        }
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase().collect { userProfile ->
                _state.update {
                    it.copy(
                        userProfile = UserProfileUiModel(
                            userName = userProfile.fullName,
                            userEmail = userProfile.email,
                            userAvatarUri = userProfile.avatarUri
                        )
                    )
                }
            }
        }
    }
}
