package com.example.shoelistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.shoelistapp.ui.ShoeApp
import com.example.shoelistapp.ui.theme.ShoeListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Membuat aplikasi tampil penuh (fullscreen/edge-to-edge) sesuai standar Android modern
        enableEdgeToEdge()

        setContent {
            // Menggunakan tema bawaan aplikasi
            ShoeListAppTheme {
                // Surface berfungsi sebagai "kanvas" dasar aplikasi
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Memanggil fungsi UI utama yang sudah kita buat di ShoeScreen.kt
                    ShoeApp()
                }
            }
        }
    }
}