package com.example.neis_job_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neis_job_app.model.JobApplication
import com.example.neis_job_app.network.RetrofitClient
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {
    private val _applications = mutableStateOf<List<JobApplication>>(emptyList())
    val applications: State<List<JobApplication>> = _applications

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun fetchApplications(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitClient.instance.getApplications(userId)
                _applications.value = response
            } catch (e: Exception) {
                _error.value = "Failed to load applications: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun submitApplication(application: JobApplication, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitClient.instance.submitApplication(application)
                if (response.success) {
                    onResult(true)
                } else {
                    _error.value = response.message
                    onResult(false)
                }
            } catch (e: Exception) {
                _error.value = "Application submission failed: ${e.message}"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
