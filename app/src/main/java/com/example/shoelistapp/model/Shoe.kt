package com.example.shoelistapp.model

import com.google.gson.annotations.SerializedName

// Nampung response dari API, soalnya dari sananya dibungkus dalam array "products"
data class ShoeResponse(
    val products: List<Shoe>
)

// Struktur data untuk masing-masing item sepatu
data class Shoe(
    val id: Int,
    @SerializedName("title") val name: String, // Di json aslinya pakai 'title', kita ubah ke 'name' biar lebih masuk akal
    val brand: String?,
    val price: Double,
    val description: String,
    @SerializedName("thumbnail") val imageUrl: String // Ambil link gambar dari properti 'thumbnail'
)