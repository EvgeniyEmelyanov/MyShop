package com.example.myshop.domain.user.usecase

import com.example.myshop.domain.user.*
import javax.inject.*

class SaveUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {

    suspend operator fun invoke(userProfile: UserProfile) {
        userProfileRepository.saveUserProfile(userProfile)
    }
}
