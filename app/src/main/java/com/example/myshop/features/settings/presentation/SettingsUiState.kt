package com.example.myshop.features.settings.presentation

import com.example.myshop.core.ui.UserProfileUiModel

data class SettingsUiState(
    val userProfile: UserProfileUiModel = UserProfileUiModel(
        userName = "",
        userEmail = "",
        userAvatarUri = null,
        userPhoneNumber = "",
        userBirthDateIso = "",
        userGender = ""
    )
)
