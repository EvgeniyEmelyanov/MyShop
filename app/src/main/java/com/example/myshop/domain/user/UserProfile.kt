package com.example.myshop.domain.user

data class UserProfile(
    val fullName: String,
    val email: String,
    val avatarUri: String?,
    val phoneNumber: String,
    val birthDateIso: String,
    val gender: String
)
