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
    val BIRTH_DATE_ISO = stringPreferencesKey("birth_date_iso")
    val GENDER = stringPreferencesKey("gender")
    val PHONE_NUMBER = stringPreferencesKey("phone_number")

    val LEGACY_DATE_BIRTHDAY = stringPreferencesKey("dateBirthday")

}
