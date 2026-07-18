package com.example.myshop.features.settings.presentation

data class SettingsUiState(
    val userProfile: UserProfileUiModel = UserProfileUiModel(
        userName = "",
        userEmail = "",
        userAvatarUri = null
    )
)