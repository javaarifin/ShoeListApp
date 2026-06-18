package com.example.shoelistapp.network

import com.example.shoelistapp.model.ShoeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ShoeApiService {
    // Tembak langsung ke endpoint khusus sepatu pria
    @GET("products/category/mens-shoes")
    suspend fun getShoes(): ShoeResponse
}

object ShoeApi {
    private const val BASE_URL = "https://dummyjson.com/"

    // Setup Retrofit pake lazy biar di-load pas dibutuhin aja (biar lebih hemat memori)
    val retrofitService: ShoeApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // Buat ngubah format JSON ke objek Kotlin otomatis
            .baseUrl(BASE_URL)
            .build()
            .create(ShoeApiService::class.java)
    }
}