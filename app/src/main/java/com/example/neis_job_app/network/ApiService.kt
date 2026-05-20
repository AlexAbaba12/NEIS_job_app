package com.example.neis_job_app.network

import com.example.neis_job_app.model.Job
import com.example.neis_job_app.model.JobApplication
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("get_jobs.php")
    suspend fun getJobs(): List<Job>

    @POST("register.php")
    suspend fun register(@Body user: Map<String, String>): ApiResponse

    @POST("login.php")
    suspend fun login(@Body credentials: Map<String, String>): LoginResponse

    @POST("apply.php")
    suspend fun submitApplication(@Body application: JobApplication): ApiResponse

    @GET("get_applications.php")
    suspend fun getApplications(@Query("user_id") userId: String): List<JobApplication>
}

data class ApiResponse(val success: Boolean, val message: String)
data class LoginResponse(val success: Boolean, val message: String, val userId: String?, val role: String?)
