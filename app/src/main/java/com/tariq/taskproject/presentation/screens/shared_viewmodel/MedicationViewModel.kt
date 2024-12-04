package com.tariq.taskproject.presentation.screens.shared_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tariq.taskproject.data.repository.MedicationRepository
import com.tariq.taskproject.models.Medication
import com.tariq.taskproject.presentation.screens.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationViewModel @Inject constructor(
    private val repository: MedicationRepository
) : ViewModel() {

    private val _medications = MutableStateFlow<List<Medication>>(emptyList())
    val medications: StateFlow<List<Medication>> = _medications.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun fetchMedications() {
        viewModelScope.launch {
            try {
                repository.fetchAndStoreMedications()
                repository.getLocalMedications().collectLatest {
                    _medications.value = it
                }
            } catch (e: Exception) {
                _medications.value = emptyList()
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isNotBlank() && password.isNotBlank()) {
                _loginState.value = LoginState.LoggedIn(username)

            } else {
                _loginState.value = LoginState.Error("Invalid credentials")
            }
        }
    }

    suspend fun getMedicationById(id: Int): Medication? =  repository.getMedicationById(id)


//    fun getMedicationById(id: Int): Medication? {
//        Log.i("Response", "getMedicationById: $id")
//        return _medications.value.find { it.id == id }
//    }
}