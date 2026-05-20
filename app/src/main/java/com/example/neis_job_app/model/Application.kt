package com.example.neis_job_app.model

import androidx.compose.runtime.mutableStateListOf

data class JobApplication(
    val id: String,
    val userId: String,
    val jobTitle: String,
    val company: String,
    val applicantName: String,
    val status: String, // e.g., "Pending", "Reviewing", "Accepted", "Rejected"
    val dateSubmitted: String
)

// In a real app, this would be in a database or ViewModel
object ApplicationRepository {
    val applications = mutableStateListOf<JobApplication>(
        JobApplication("1", "user123", "Software Engineer", "Naga Tech Solutions", "Juan Dela Cruz", "Reviewing", "2023-10-25"),
        JobApplication("2", "user123", "Data Analyst", "Bicol Data Systems", "Juan Dela Cruz", "Pending", "2023-10-27")
    )

    fun addApplication(application: JobApplication) {
        applications.add(application)
    }
}
