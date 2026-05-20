package com.example.neis_job_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neis_job_app.network.RetrofitClient
import com.example.neis_job_app.utils.SessionManager
import kotlinx.coroutines.launch

class AuthViewModel(private val sessionManager: SessionManager) : ViewModel() {
    private val _userId = mutableStateOf<String?>(null)
    val userId: State<String?> = _userId

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _rememberMe = mutableStateOf(false)
    val rememberMe: State<Boolean> = _rememberMe

    private val _savedEmail = mutableStateOf("")
    val savedEmail: State<String> = _savedEmail

    init {
        viewModelScope.launch {
            sessionManager.userId.collect { id ->
                _userId.value = id
            }
        }
        viewModelScope.launch {
            sessionManager.rememberMe.collect { enabled ->
                _rememberMe.value = enabled
            }
        }
        viewModelScope.launch {
            sessionManager.savedEmail.collect { email ->
                _savedEmail.value = email ?: ""
            }
        }
    }

    fun setRememberMe(enabled: Boolean) {
        _rememberMe.value = enabled
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val credentials = mapOf("email" to email, "password" to password)
                val response = RetrofitClient.instance.login(credentials)
                if (response.success && response.userId != null) {
                    _userId.value = response.userId
                    sessionManager.saveUserId(response.userId)
                    sessionManager.saveRememberMe(_rememberMe.value, if (_rememberMe.value) email else null)
                    onResult(true)
                } else {
                    _error.value = response.message
                    onResult(false)
                }
            } catch (e: Exception) {
                _error.value = "Login failed: ${e.message}"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(name: String, email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val user = mapOf("name" to name, "email" to email, "password" to password)
                val response = RetrofitClient.instance.register(user)
                if (response.success) {
                    // After successful registration, usually you'd login or just go to login screen
                    // For now, let's just trigger result
                    onResult(true)
                } else {
                    _error.value = response.message
                    onResult(false)
                }
            } catch (e: Exception) {
                _error.value = "Registration failed: ${e.message}"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _userId.value = null
        }
    }
}
