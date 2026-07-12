package com.example.myshop.domain.user

import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    fun observeUserProfile(): Flow<UserProfile>

    suspend fun saveUserProfile(userProfile: UserProfile)
}
