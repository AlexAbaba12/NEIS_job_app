package com.example.neis_job_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Replace with your server's IP address or domain
    // Use 10.0.2.2 for Android Emulator to access localhost
    private const val BASE_URL = "http://10.0.2.2/neis_api/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
