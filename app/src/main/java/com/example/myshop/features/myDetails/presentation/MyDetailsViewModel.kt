package com.example.myshop.features.myDetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.core.ui.UserProfileUiModel
import com.example.myshop.domain.user.UserProfile
import com.example.myshop.domain.user.usecase.ObserveUserProfileUseCase
import com.example.myshop.domain.user.usecase.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDetailsViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyDetailsUiState())
    val state = _state.asStateFlow()

    init {
        observeUserProfile()
    }

    fun onSaveClick() {
        val editableProfile = _state.value.editableProfile

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }

            saveUserProfileUseCase(
                UserProfile(
                    fullName = editableProfile.userName,
                    email = editableProfile.userEmail,
                    avatarUri = editableProfile.userAvatarUri,
                    phoneNumber = editableProfile.userPhoneNumber,
                    birthDateIso = editableProfile.userBirthDateIso,
                    gender = editableProfile.userGender
                )
            )

            _state.update {
                it.copy(
                    isEditMode = false,
                    isSaving = false
                )
            }
        }
    }

    fun onCancelClick() {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.profile,
                isEditMode = false,
                isSaving = false
            )
        }
    }

    fun onNameChanged(name: String) {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.editableProfile.copy(
                    userName = name
                )
            )
        }
    }

    fun onEmailChanged(email: String) {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.editableProfile.copy(
                    userEmail = email
                )
            )
        }
    }

    fun onPhoneChanged(phone: String) {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.editableProfile.copy(
                    userPhoneNumber = phone
                )
            )
        }
    }

    fun onDateChanged(date: String) {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.editableProfile.copy(
                    userBirthDateIso = date
                )
            )
        }
    }

    fun onGenderChanged(gender: String) {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.editableProfile.copy(
                    userGender = gender
                )
            )
        }
    }

    fun onEditClick() {
        _state.update { currentState ->
            currentState.copy(
                editableProfile = currentState.profile,
                isEditMode = true
            )
        }
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            observeUserProfileUseCase().collect { userProfile ->
                _state.update {
                    it.copy(
                        profile = UserProfileUiModel(
                            userName = userProfile.fullName,
                            userEmail = userProfile.email,
                            userAvatarUri = userProfile.avatarUri,
                            userPhoneNumber = userProfile.phoneNumber,
                            userBirthDateIso = userProfile.birthDateIso,
                            userGender = userProfile.gender
                        )
                    )
                }
            }
        }
    }

}
