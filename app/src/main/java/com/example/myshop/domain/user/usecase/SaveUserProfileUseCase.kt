package com.example.myshop.domain.user.usecase

import com.example.myshop.domain.user.UserProfile
import com.example.myshop.domain.user.UserProfileRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend operator fun invoke(userProfile: UserProfile) {
        userProfileRepository.saveUserProfile(userProfile)
    }
}
