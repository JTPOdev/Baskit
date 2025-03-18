package com.example.splashscreenbaskit.Home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashscreenbaskit.api.ApiService
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var dagupanVendors by mutableStateOf<List<Vendor>>(emptyList())
        private set
    var calasiaoVendors by mutableStateOf<List<Vendor>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val apiService: ApiService = RetrofitInstance.create(ApiService::class.java)

    fun fetchDagupanVendors() {
        viewModelScope.launch {
            isLoading = true
            try {
                dagupanVendors = apiService.getDagupanVendors()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching Dagupan vendors: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun fetchCalasiaoVendors() {
        viewModelScope.launch {
            isLoading = true
            try {
                calasiaoVendors = apiService.getCalasiaoVendors()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error fetching Calasiao vendors: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}