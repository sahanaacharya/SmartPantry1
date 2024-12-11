package com.example.smartpantry.data.api

import ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.spoonacular.com/" // Corrected base URL

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Set the base URL
            .addConverterFactory(GsonConverterFactory.create()) // Add JSON converter
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java) // Create the API interface
    }
}





