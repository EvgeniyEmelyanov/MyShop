package com.example.myshop.data.user.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.myshop.data.user.local.UserProfilePreferencesKeys
import com.example.myshop.data.user.local.userProfileDataStore
import com.example.myshop.domain.user.UserProfile
import com.example.myshop.domain.user.UserProfileRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : UserProfileRepository {

    override fun observeUserProfile(): Flow<UserProfile> {
        return context.userProfileDataStore.data.map { preferences ->
            UserProfile(
                fullName = preferences[UserProfilePreferencesKeys.FULL_NAME] ?: "Your name",
                email = preferences[UserProfilePreferencesKeys.EMAIL] ?: "your.email@example.com",
                avatarUri = preferences[UserProfilePreferencesKeys.AVATAR_URI]
            )
        }
    }

    override suspend fun saveUserProfile(userProfile: UserProfile) {
        context.userProfileDataStore.edit { preferences ->
            preferences[UserProfilePreferencesKeys.FULL_NAME] = userProfile.fullName
            preferences[UserProfilePreferencesKeys.EMAIL] = userProfile.email
            userProfile.avatarUri?.let { avatarUri ->
                preferences[UserProfilePreferencesKeys.AVATAR_URI] = avatarUri
            } ?: preferences.remove(UserProfilePreferencesKeys.AVATAR_URI)
        }
    }
}
