package com.example.shoelistapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoelistapp.model.Shoe
import com.example.shoelistapp.network.ShoeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ShoeViewModel : ViewModel() {

    private val _shoeUiState = MutableStateFlow<ShoeUiState>(ShoeUiState.Loading)
    val shoeUiState: StateFlow<ShoeUiState> = _shoeUiState.asStateFlow()


    //buat nyimpen data sepatu yang lagi dipilih/diklik buat diliat detailnya
    private val _selectedShoe = MutableStateFlow<Shoe?>(null)
    val selectedShoe: StateFlow<Shoe?> = _selectedShoe.asStateFlow()

    init {
        getShoeList()
    }

    fun getShoeList() {
        viewModelScope.launch {
            _shoeUiState.value = ShoeUiState.Loading
            try {
                val response = ShoeApi.retrofitService.getShoes()
                _shoeUiState.value = ShoeUiState.Success(response.products)
            } catch (e: IOException) {
                _shoeUiState.value = ShoeUiState.Error
            } catch (e: Exception) {
                _shoeUiState.value = ShoeUiState.Error
            }
        }
    }

    // Fungsi buat ngatur sepatu mana yang lagi aktif dipilih user
    fun selectShoe(shoe: Shoe) {
        _selectedShoe.value = shoe
    }
}