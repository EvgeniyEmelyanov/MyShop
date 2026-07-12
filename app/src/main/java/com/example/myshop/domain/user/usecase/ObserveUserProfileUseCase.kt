package com.example.myshop.domain.user.usecase

import com.example.myshop.domain.user.UserProfile
import com.example.myshop.domain.user.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveUserProfileUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository
) {

    operator fun invoke(): Flow<UserProfile> {
        return userProfileRepository.observeUserProfile()
    }
}
