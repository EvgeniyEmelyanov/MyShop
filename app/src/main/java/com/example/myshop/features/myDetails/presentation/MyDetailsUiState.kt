package com.example.myshop.features.myDetails.presentation

import com.example.myshop.core.ui.UserProfileUiModel

data class MyDetailsUiState(
    val profile: UserProfileUiModel = UserProfileUiModel(
        userName = "",
        userEmail = "",
        userAvatarUri = null,
        userPhoneNumber = "",
        userBirthDateIso = "",
        userGender = ""
    ),
    val editableProfile: UserProfileUiModel = UserProfileUiModel(
        userName = "",
        userEmail = "",
        userAvatarUri = null,
        userPhoneNumber = "",
        userBirthDateIso = "",
        userGender = ""
    ),
    val isEditMode: Boolean = false,
    val isSaving: Boolean = false
)
