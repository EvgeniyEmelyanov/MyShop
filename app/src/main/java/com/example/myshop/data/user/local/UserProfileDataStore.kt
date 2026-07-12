package com.example.myshop.data.user.local

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val USER_PROFILE_DATA_STORE_NAME = "user_profile"

val Context.userProfileDataStore by preferencesDataStore(
    name = USER_PROFILE_DATA_STORE_NAME
)

object UserProfilePreferencesKeys {

    val FULL_NAME = stringPreferencesKey("full_name")
    val EMAIL = stringPreferencesKey("email")
    val AVATAR_URI = stringPreferencesKey("avatar_uri")
}