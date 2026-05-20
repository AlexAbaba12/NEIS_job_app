package com.example.neis_job_app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neis_job_app.model.Job
import com.example.neis_job_app.network.RetrofitClient
import kotlinx.coroutines.launch

class JobViewModel : ViewModel() {
    private val _jobs = mutableStateOf<List<Job>>(emptyList())
    val jobs: State<List<Job>> = _jobs

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        fetchJobs()
    }

    fun fetchJobs() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitClient.instance.getJobs()
                _jobs.value = response
            } catch (e: Exception) {
                _error.value = "Failed to load jobs: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
