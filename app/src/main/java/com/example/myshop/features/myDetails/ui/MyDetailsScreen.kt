package com.example.myshop.features.myDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myshop.R
import com.example.myshop.features.myDetails.presentation.MyDetailsUiState

@Composable
fun DetailsScreen(
    state: MyDetailsUiState,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit
) {
    val profile = state.profile
    val editableProfile = state.editableProfile

    val isSaveEnabled = with(editableProfile) {
        userName.isNotBlank()
                && userEmail.isNotBlank()
                && userPhoneNumber.isNotBlank()
                && userBirthDateIso.isNotBlank()
                && userGender.isNotBlank()
    }

    val detailsItems = listOf(
        UserDetailsDisplayItem(
            icon = Icons.Default.Person,
            title = stringResource(id = R.string.name),
            value = profile.userName
        ),
        UserDetailsDisplayItem(
            icon = Icons.Outlined.Email,
            title = stringResource(id = R.string.email),
            value = profile.userEmail
        ),
        UserDetailsDisplayItem(
            icon = Icons.Outlined.Phone,
            title = stringResource(id = R.string.phone_number),
            value = profile.userPhoneNumber
        ),
        UserDetailsDisplayItem(
            icon = Icons.Outlined.DateRange,
            title = stringResource(id = R.string.date_of_birth),
            value = profile.userBirthDateIso.toDisplayBirthDate()
        ),
        UserDetailsDisplayItem(
            icon = Icons.Default.Wc,
            title = stringResource(id = R.string.gender),
            value = profile.userGender.toDisplayGender()
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 25.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 25.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    onBackClick()
                },
                enabled = !state.isEditMode,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer { alpha = if (!state.isEditMode) 1f else 0f }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = stringResource(id = R.string.my_details_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            IconButton(
                onClick = onEditClick,
                enabled = !state.isEditMode,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer { alpha = if (!state.isEditMode) 1f else 0f }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(id = R.string.edit_profile),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .size(160.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Outlined.AddAPhoto,
                        contentDescription = stringResource(id = R.string.my_details_add_photo),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.isEditMode) {
                    EditProfileContent(
                        editableProfile = editableProfile,
                        isSaveEnabled = isSaveEnabled,
                        isSaving = state.isSaving,
                        onNameChanged = onNameChanged,
                        onEmailChanged = onEmailChanged,
                        onPhoneChanged = onPhoneChanged,
                        onDateChanged = onDateChanged,
                        onGenderChanged = onGenderChanged,
                        onCancelClick = onCancelClick,
                        onSaveClick = onSaveClick
                    )
                } else {
                    detailsItems.forEach { item ->
                        UserDetailsItem(
                            icon = item.icon,
                            title = item.title,
                            value = item.value
                        )
                    }
                }
            }
        }
    }
}

private data class UserDetailsDisplayItem(
    val icon: ImageVector,
    val title: String,
    val value: String
)

@Preview(showBackground = true)
@Composable
fun MyDetailsScreenPreview() {
    DetailsScreen(
        state = MyDetailsUiState(
            isEditMode = false,
        ),
        onEditClick = {},
        onBackClick = {},
        onCancelClick = {},
        onSaveClick = {},
        onNameChanged = {},
        onEmailChanged = {},
        onPhoneChanged = {},
        onDateChanged = {},
        onGenderChanged = {}
    )
}
