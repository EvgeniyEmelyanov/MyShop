package com.example.myshop.data.user.repository

import android.content.*
import androidx.datastore.preferences.core.*
import com.example.myshop.data.user.local.*
import com.example.myshop.domain.user.*
import dagger.hilt.android.qualifiers.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class UserProfileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
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