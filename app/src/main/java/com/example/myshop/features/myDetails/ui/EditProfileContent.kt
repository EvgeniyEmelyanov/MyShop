package com.example.myshop.features.myDetails.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myshop.R
import com.example.myshop.core.ui.UserProfileUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileContent(
    editableProfile: UserProfileUiModel,
    isSaveEnabled: Boolean,
    isSaving: Boolean,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onGenderChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val selectedBirthDateMillis = editableProfile.userBirthDateIso.toUtcMillisOrNull()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedBirthDateMillis,
        selectableDates = PastOrTodaySelectableDates
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfileTextField(
            value = editableProfile.userName,
            onValueChange = onNameChanged,
            label = stringResource(id = R.string.name)
        )

        ProfileTextField(
            value = editableProfile.userEmail,
            onValueChange = onEmailChanged,
            label = stringResource(id = R.string.email),
            keyboardType = KeyboardType.Email
        )

        ProfileTextField(
            value = editableProfile.userPhoneNumber,
            onValueChange = onPhoneChanged,
            label = stringResource(id = R.string.phone_number),
            keyboardType = KeyboardType.Phone
        )

        DatePickerTextField(
            value = editableProfile.userBirthDateIso.toDisplayBirthDate(),
            label = stringResource(id = R.string.date_of_birth),
            isSelected = isDatePickerVisible,
            onClick = { isDatePickerVisible = true }
        )

        GenderSelector(
            selectedGender = editableProfile.userGender,
            onGenderSelected = onGenderChanged
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextButton(
                modifier = Modifier.weight(1f),
                enabled = !isSaving,
                onClick = onCancelClick
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(
                modifier = Modifier.weight(1f),
                enabled = isSaveEnabled && !isSaving,
                onClick = onSaveClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }

    if (isDatePickerVisible) {
        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis
                            ?.toIsoLocalDate()
                            ?.let(onDateChanged)
                        isDatePickerVisible = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerVisible = false }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private object PastOrTodaySelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }
}
