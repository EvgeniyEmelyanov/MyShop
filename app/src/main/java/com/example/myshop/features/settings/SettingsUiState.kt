package com.example.myshop.features.settings

data class SettingsUiState(
    val userProfile: UserProfileUiModel = UserProfileUiModel(
        userName = "",
        userEmail = "",
        userAvatarUri = null
    )
)