package com.example.myshop.core.ui

data class UserProfileUiModel(
    val userName: String,
    val userEmail: String,
    val userAvatarUri: String?,
    val userPhoneNumber: String,
    val userBirthDateIso: String,
    val userGender: String

)
