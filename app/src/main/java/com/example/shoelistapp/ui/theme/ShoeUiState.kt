package com.example.shoelistapp.ui

import com.example.shoelistapp.model.Shoe

// Bikin 3 state utama buat ngatur tampilan aplikasi, biar gampang dipanggil di UI nanti
sealed interface ShoeUiState {
    data class Success(val shoes: List<Shoe>) : ShoeUiState
    object Error : ShoeUiState
    object Loading : ShoeUiState
}