package com.tariq.taskproject.presentation.screens.login

sealed class LoginState {
    object Initial : LoginState()
    data class LoggedIn(val username: String) : LoginState()
    data class Error(val message: String) : LoginState()
}