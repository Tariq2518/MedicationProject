package com.tariq.taskproject.presentation.screens.details_screen

import com.tariq.taskproject.models.Medication

sealed class MedicationUiState {
    object Loading : MedicationUiState()
    data class Success(val medication: Medication) : MedicationUiState()
    data class Error(val message: String? = null) : MedicationUiState()
}